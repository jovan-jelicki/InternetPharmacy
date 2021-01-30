package app.dto;

import app.model.appointment.Appointment;

import java.time.LocalDateTime;

public class AppointmentFinishedDTO {
    private String pharmacyName;
    private String patientFirstName;
    private String patientLastName;
    private LocalDateTime dateOfAppointment;

    public AppointmentFinishedDTO(Appointment appointment) {
        this.pharmacyName = appointment.getPharmacy().getName();
        this.patientFirstName = appointment.getPatient().getFirstName();
        this.patientLastName = appointment.getPatient().getLastName();
        this.dateOfAppointment = appointment.getPeriod().getPeriodStart();
    }

    public AppointmentFinishedDTO() {
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public LocalDateTime getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(LocalDateTime dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }
}
