package app.service;

import app.model.appointment.Appointment;
import app.model.user.Pharmacist;
import app.dto.AppointmentSearchDTO;

import java.time.LocalDateTime;
import java.util.Collection;

public interface CounselingService {
    Collection<Appointment> findPreviousByPatientId(Long patientId);
    Collection<Pharmacist> findAvailablePharmacists(AppointmentSearchDTO appointmentSearchKit);
    Collection<Appointment> findUpcomingByPatientId(Long patientId);
}
