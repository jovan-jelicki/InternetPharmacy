package app.model.appointment;

import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.model.user.EmployeeType;
import app.model.user.Patient;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AppointmentCancelled {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_generator")
    @SequenceGenerator(name="appointment_generator", sequenceName = "appointment_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    @Column
    private Long examinerId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private Pharmacy pharmacy;

    @Enumerated(EnumType.ORDINAL)
    private AppointmentStatus appointmentStatus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private Patient patient;

    @Enumerated(EnumType.ORDINAL)
    private EmployeeType type;

    private Period period;

    public AppointmentCancelled(Appointment a) {
        examinerId = a.getExaminerId();
        pharmacy = a.getPharmacy();
        patient = a.getPatient();
        appointmentStatus = AppointmentStatus.cancelled;
        type = a.getType();
        period = a.getPeriod();
    }

    public AppointmentCancelled() {}

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

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public boolean isOverlapping(LocalDateTime timeSlot) {
        LocalDateTime start = period.getPeriodStart().minusMinutes(1);
        LocalDateTime end = period.getPeriodEnd().plusMinutes(1);
        return (start.isBefore(timeSlot) && end.isAfter(timeSlot)) ||
                (start.isBefore(timeSlot.plusHours(1)) && end.isAfter(timeSlot.plusHours(1)));
    }
}
