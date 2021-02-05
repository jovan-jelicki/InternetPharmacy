package app.service;

import app.dto.AppointmentUpdateDTO;
import app.model.appointment.Appointment;

import java.util.Collection;

public interface ExaminationService {
    Collection<Appointment> findPreviousByPatientId(Long patientId);
    Collection<Appointment> findUpcomingByPatientId(Long patientId);
    Boolean dermatologistScheduling(Appointment appointment);
    Boolean dermatologistSchedulingCreatedAppointment(AppointmentUpdateDTO appointmentUpdateDTO);
    }
