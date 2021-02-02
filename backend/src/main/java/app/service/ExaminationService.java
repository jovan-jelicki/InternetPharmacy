package app.service;

import app.model.appointment.Appointment;

import java.util.Collection;

public interface ExaminationService {
    Collection<Appointment> findPreviousByPatientId(Long patientId);
    Collection<Appointment> findUpcomingByPatientId(Long patientId);
}
