package app.service.impl;

import app.dto.AppointmentSearchDTO;

import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.time.Period;

import app.model.time.VacationRequest;
import app.model.time.VacationRequestStatus;
import app.model.user.EmployeeType;
import app.model.user.Pharmacist;
import app.repository.VacationRequestRepository;
import app.service.AppointmentService;
import app.service.CounselingService;
import app.service.PatientService;
import app.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CounselingServiceImpl implements CounselingService {
    private final AppointmentService appointmentService;
    private final PharmacistService pharmacistService;
    private final VacationRequestRepository vacationRequestRepository;
    private final PatientService patientService;

    @Autowired
    public CounselingServiceImpl(PatientService patientService, AppointmentService appointmentService, PharmacistService pharmacistService,
                                 VacationRequestRepository vacationRequestRepository) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.pharmacistService = pharmacistService;
        this.vacationRequestRepository = vacationRequestRepository;
    }


    @Override
    public Boolean pharmacistScheduling(Appointment appointment){
        if(!isConsultationPossible(appointment.getExaminerId(), appointment.getPatient().getId(), appointment.getPeriod().getPeriodStart()))
            return false;
        appointment.setAppointmentStatus(AppointmentStatus.available);
        appointment.setActive(true);
        Period period = new Period();
        period.setPeriodStart(appointment.getPeriod().getPeriodStart());
        period.setPeriodEnd(appointment.getPeriod().getPeriodStart().plusHours(1));
        appointment.setPeriod(period);
        appointment.setPatient(patientService.read(appointment.getPatient().getId()).get());
        appointmentService.save(appointment);
        return true;
    }

    public Boolean isConsultationPossible(Long pharmacistId, Long patientId, LocalDateTime localDateTime){
        Pharmacist pharmacist = pharmacistService.read(pharmacistId).get();
        if(pharmacist == null)
            return false;
        AppointmentSearchDTO appointmentSearchDTO = new AppointmentSearchDTO();
        appointmentSearchDTO.setPatientId(patientId);
        appointmentSearchDTO.setTimeSlot(localDateTime);
        if(findAvailablePharmacists(appointmentSearchDTO).stream().filter(f -> f.getId() == pharmacistId).findFirst().orElse(null) == null)
            return false;
        if(appointmentService.getAllNotFinishedByPatientId(patientId)
                .stream().filter(a -> a.isOverlapping(localDateTime)).findFirst().orElse(null) != null)
            return false;

        return true;
    }

    @Override
    public Collection<Appointment> findUpcomingByPatientId(Long patientId) {
        Collection<Appointment> appointments = appointmentService
                .findAppointmentsByPatient_IdAndType(patientId, EmployeeType.pharmacist);
        return appointments
                .stream()
                .filter(a -> a.getPeriod().getPeriodStart().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Appointment> findPreviousByPatientId(Long patientId) {
        Collection<Appointment> appointments = appointmentService
                .findAppointmentsByPatient_IdAndType(patientId, EmployeeType.pharmacist);
        Collection<Appointment> cancelled = appointmentService
                .findCancelledByPatientIdAndType(patientId, EmployeeType.pharmacist);
        Collection<Appointment> all = appointments
                                        .stream()
                                        .filter(a -> a.getPeriod().getPeriodStart().isBefore(LocalDateTime.now()))
                                        .collect(Collectors.toList());
        all.addAll(cancelled);
        return all;
    }

    @Override
    public Collection<Pharmacist> findAvailablePharmacists(AppointmentSearchDTO appointmentSearchKit) {
        LocalDateTime dateTime = appointmentSearchKit.getTimeSlot();
        Long patientId = appointmentSearchKit.getPatientId();
        Set<Pharmacist> unavailable = findUnavailable(dateTime, patientId);
        Set<Pharmacist> available = findAvailable(dateTime);
        available.removeAll(unavailable);
        return available;
    }

    private Set<Pharmacist> findAvailable(LocalDateTime dateTime) {
        Set<Pharmacist> available = new HashSet<>();
        Collection<Pharmacist> pharmacists = pharmacistService.read();
        pharmacists.forEach(p -> {
            if(p.isOverlapping(dateTime) && !isOnVacation(dateTime, p.getId()))
                available.add(p);
        });
        return available;
    }

    private Set<Pharmacist> findUnavailable(LocalDateTime dateTime, Long patientId) {
        Set<Pharmacist> unavailable = new HashSet<>();
        Collection<Appointment> scheduledAvailable = appointmentService
                .findAppointmentsByPatientNotNullAndType(EmployeeType.pharmacist);
        scheduledAvailable.forEach(a -> {
            Pharmacist pharmacist = pharmacistService.read(a.getExaminerId()).get();
            if(a.isOverlapping(dateTime)) {
                unavailable.add(pharmacist);
            }
        });
        unavailable.addAll(findUnavailableCanceled(dateTime, patientId));
        return unavailable;
    }

    private Set<Pharmacist> findUnavailableCanceled(LocalDateTime dateTime, Long patientId) {
        Set<Pharmacist> unavailable = new HashSet<>();
        Collection<Appointment> scheduledCancelled = appointmentService
                .findCancelledByPatientIdAndType(patientId, EmployeeType.pharmacist);
        scheduledCancelled.forEach(a -> {
            Pharmacist pharmacist = pharmacistService.read(a.getExaminerId()).get();
            if(a.isOverlapping(dateTime)) {
                unavailable.add(pharmacist);
            }
        });
        return unavailable;
    }

    private boolean isOnVacation(LocalDateTime dateTime, Long id) {
        Collection<VacationRequest> vacations = vacationRequestRepository
                .findByEmployeeIdAndEmployeeTypeAndVacationRequestStatus(id, EmployeeType.pharmacist, VacationRequestStatus.approved);
        for (VacationRequest vacation: vacations) {
            LocalDateTime start = vacation.getPeriod().getPeriodStart();
            LocalDateTime end = vacation.getPeriod().getPeriodEnd();
            if(start.isBefore(dateTime) && end.isAfter(dateTime))
                return true;
        }
        return false;
    }
}
