package app.dto;

import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.appointment.Therapy;
import app.model.time.Period;
import app.model.user.EmployeeType;

public class AppointmentScheduledDTO {
    private Long id;
    private Long examinerId;
    private String report;
    private Long pharmacyId;
    private String pharmacyName;
    private AppointmentStatus appointmentStatus;
    private Long patientId;
    private String firstName;
    private String lastName;
    private EmployeeType type;
    private Therapy therapy;
    private Period period;

    public AppointmentScheduledDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.examinerId = appointment.getExaminerId();
        this.report = appointment.getReport();
        this.pharmacyId = appointment.getPharmacy().getId();
        this.pharmacyName = appointment.getPharmacy().getName();
        this.appointmentStatus = appointment.getAppointmentStatus();
        this.patientId = appointment.getPatient().getId();
        this.firstName = appointment.getPatient().getFirstName();
        this.lastName = appointment.getPatient().getLastName();
        this.type = appointment.getType();
        this.therapy = appointment.getTherapy();
        this.period = appointment.getPeriod();
    }

    public AppointmentScheduledDTO() {
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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


    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
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
