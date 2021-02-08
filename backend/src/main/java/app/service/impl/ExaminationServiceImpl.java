package app.service.impl;

import app.dto.AppointmentUpdateDTO;
import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.time.Period;
import app.model.user.EmployeeType;
import app.model.user.Patient;
import app.service.AppointmentService;
import app.service.ExaminationService;
import app.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class ExaminationServiceImpl implements ExaminationService {

    private final AppointmentService appointmentService;
    private final PatientService patientService;

    @Autowired
    public ExaminationServiceImpl(PatientService patientService, AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean dermatologistSchedulingCreatedAppointment(AppointmentUpdateDTO appointmentUpdateDTO){
        Appointment appointment = appointmentService.read(appointmentUpdateDTO.getAppointmentId()).get();
        if(appointmentService.getAllNotFinishedByPatientId(appointmentUpdateDTO.getPatientId())
                .stream().filter(a -> a.isOverlapping(appointment.getPeriod().getPeriodStart())).findFirst().orElse(null) != null)
            return false;
        Patient patient = patientService.read(appointmentUpdateDTO.getPatientId()).get();
        appointment.setPatient(patient);
        appointmentService.save(appointment);
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean dermatologistScheduling(Appointment appointment){
        if(isExaminationPossible(appointment)) {
            appointment.setAppointmentStatus(AppointmentStatus.available);
            appointment.setActive(true);
            Period period = new Period();
            period.setPeriodStart(appointment.getPeriod().getPeriodStart());
            period.setPeriodEnd(appointment.getPeriod().getPeriodStart().plusHours(1));
            appointment.setPeriod(period);
            appointment.setPatient(patientService.read(appointment.getPatient().getId()).get());
            return appointmentService.createAvailableAppointment(appointment);
        }
        return  false;
    }

    public Boolean isExaminationPossible(Appointment appointment) {
        if(appointmentService.getAllNotFinishedByPatientId(appointment.getPatient().getId())
                .stream().filter(a -> a.isOverlapping(appointment.getPeriod().getPeriodStart())).findFirst().orElse(null) != null)
            return false;
        if (!appointmentService.validateAppointmentTimeRegardingWorkingHours(appointment))
            return false;
        if (!appointmentService.validateAppointmentTimeRegardingAllWorkingHours(appointment))
            return false;
        else if (!appointmentService.validateAppointmentTimeRegardingVacationRequests(appointment))
            return false;

        return true;
    }



        @Override
    public Collection<Appointment> findPreviousByPatientId(Long patientId) {
        Collection<Appointment> appointments = appointmentService
                .findAppointmentsByPatient_IdAndType(patientId, EmployeeType.ROLE_dermatologist);
        Collection<Appointment> cancelled = appointmentService
                .findCancelledByPatientIdAndType(patientId, EmployeeType.ROLE_dermatologist);
        Collection<Appointment> all = appointments
                .stream()
                .filter(a -> a.getPeriod().getPeriodStart().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
        all.addAll(cancelled);
        return all;
    }

    @Override
    public Collection<Appointment> findUpcomingByPatientId(Long patientId) {
        Collection<Appointment> appointments = appointmentService
                .findAppointmentsByPatient_IdAndType(patientId, EmployeeType.ROLE_dermatologist);
        return appointments
                .stream()
                .filter(a -> a.getPeriod().getPeriodStart().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }
}
