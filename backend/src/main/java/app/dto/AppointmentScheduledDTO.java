package app.dto;

import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.appointment.Therapy;
import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.model.user.EmployeeType;
import app.model.user.Patient;

public class AppointmentScheduledDTO {
    private Long id;
    private Long examinerId;
    private String report;
    private Pharmacy pharmacy = new Pharmacy();
    private AppointmentStatus appointmentStatus;
    private Patient patient = new Patient();
    private EmployeeType type;
    private double cost;
    private Therapy therapy;
    private Period period;

    public AppointmentScheduledDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.examinerId = appointment.getExaminerId();
        this.report = appointment.getReport();
        this.pharmacy.setId(appointment.getPharmacy().getId());
        this.pharmacy.setName(appointment.getPharmacy().getName());
        this.appointmentStatus = appointment.getAppointmentStatus();
        this.patient.setId(appointment.getPatient().getId());
        this.patient.setFirstName(appointment.getPatient().getFirstName());
        this.patient.setLastName(appointment.getPatient().getLastName());
        this.type = appointment.getType();
        this.cost = appointment.getCost();
        this.therapy = appointment.getTherapy();
        this.period = appointment.getPeriod();
    }

    public AppointmentScheduledDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExaminerId() {
        return examinerId;
    }

    public void setExaminerId(Long examinerId) {
        this.examinerId = examinerId;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Therapy getTherapy() {
        return therapy;
    }

    public void setTherapy(Therapy therapy) {
        this.therapy = therapy;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
