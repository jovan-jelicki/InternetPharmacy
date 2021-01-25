package app.service.impl;

import app.model.appointment.Appointment;
import app.service.SchedulingStrategy;

import java.time.LocalDateTime;
import java.util.Collection;

public class ExaminationSchedulingStrategy implements SchedulingStrategy {
    @Override
    public Collection<Appointment> findAvailable(Collection<Appointment> appointments, LocalDateTime dateTime) {
        return null;
    }
}
