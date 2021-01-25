package app.service;

import app.model.appointment.Appointment;

import java.time.LocalDateTime;
import java.util.Collection;

public interface SchedulingStrategy {
    Collection<Appointment> findAvailable(Collection<Appointment> appointments, LocalDateTime dateTime);
}
