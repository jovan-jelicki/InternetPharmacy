package app.service;

import app.dto.AppointmentSearchDTO;
import app.model.appointment.Appointment;
import app.model.user.Pharmacist;

import java.util.Collection;

public interface CounselingService {
    Collection<Appointment> findPreviousByPatientId(Long patientId);
    Collection<Pharmacist> findAvailablePharmacists(AppointmentSearchDTO appointmentSearchKit);
    Collection<Appointment> findUpcomingByPatientId(Long patientId);
    Boolean pharmacistScheduling(Appointment appointment);

    }
