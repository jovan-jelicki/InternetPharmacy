package app.service;

import app.dto.AppointmentSearchDTO;
import app.model.appointment.Appointment;
import app.model.user.Pharmacist;

import java.util.Collection;

public interface ExaminationService {
    Collection<Appointment> findPreviousByPatientId(Long patientId);
    Collection<Appointment> findUpcomingByPatientId(Long patientId);
    Boolean dermatologistScheduling(Appointment appointment);
    }
