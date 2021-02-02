package app.service.impl;

import app.model.appointment.Appointment;
import app.model.user.EmployeeType;
import app.service.AppointmentService;
import app.service.ExaminationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ExaminationServiceImpl implements ExaminationService {
    AppointmentService appointmentService;

    public ExaminationServiceImpl(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public Collection<Appointment> findPreviousByPatientId(Long patientId) {
        Collection<Appointment> appointments = appointmentService
                .findAppointmentsByPatient_IdAndType(patientId, EmployeeType.dermatologist);
        Collection<Appointment> cancelled = appointmentService
                .findCancelledByPatientIdAndType(patientId, EmployeeType.dermatologist);
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
                .findAppointmentsByPatient_IdAndType(patientId, EmployeeType.dermatologist);
        return appointments
                .stream()
                .filter(a -> a.getPeriod().getPeriodStart().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }
}
