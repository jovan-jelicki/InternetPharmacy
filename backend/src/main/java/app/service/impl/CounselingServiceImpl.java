package app.service.impl;

import app.dto.AppointmentSearchDTO;

import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.time.VacationRequest;
import app.model.time.VacationRequestStatus;
import app.model.user.EmployeeType;
import app.model.user.Pharmacist;
import app.repository.VacationRequestRepository;
import app.service.AppointmentService;
import app.service.CounselingService;
import app.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    public CounselingServiceImpl(AppointmentService appointmentService, PharmacistService pharmacistService,
                                 VacationRequestRepository vacationRequestRepository) {
        this.appointmentService = appointmentService;
        this.pharmacistService = pharmacistService;
        this.vacationRequestRepository = vacationRequestRepository;
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
