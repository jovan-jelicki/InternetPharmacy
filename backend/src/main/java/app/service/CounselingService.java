package app.service;

import app.model.appointment.Appointment;
import app.model.user.Pharmacist;

import java.time.LocalDateTime;
import java.util.Collection;

public interface CounselingService {
    Collection<Pharmacist> findAvailablePharmacists(LocalDateTime dateTime);
    Collection<Appointment> findUpcomingByPatientId(Long patientId);
    Boolean pharmacistScheduling(Appointment appointment);

    }
