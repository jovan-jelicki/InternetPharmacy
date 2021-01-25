package app.service;

import app.dto.AppointmentSearchDTO;
import app.model.appointment.Appointment;

import java.time.LocalDateTime;
import java.util.Collection;

public interface AppointmentService {
    public Collection<Appointment> findAvailable(LocalDateTime dateTime);
    public void setSchedulingStrategy(SchedulingStrategy schedulingStrategy);
}
