package app.service.impl;

import app.dto.AppointmentSearchDTO;
import app.model.appointment.Appointment;
import app.model.user.EmployeeType;
import app.model.user.Pharmacist;
import app.repository.AppointmentRepository;
import app.service.AppointmentService;
import app.service.SchedulingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    AppointmentRepository appointmentRepository;

    SchedulingStrategy schedulingStrategy;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void setSchedulingStrategy(SchedulingStrategy schedulingStrategy) {
        this.schedulingStrategy = schedulingStrategy;
    }

    @Override
    public Collection<Appointment> findAvailable(LocalDateTime dateTime) {
        Collection<Appointment> scheduled = appointmentRepository.findAppointmentsByPatientNotNull();
        return schedulingStrategy.findAvailable(scheduled,dateTime);
    }
}
