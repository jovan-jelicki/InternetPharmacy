package app.model.appointment;

import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.model.user.EmployeeType;
import app.model.user.Patient;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"examinerId", "type", "periodStart"})})
public class Appointment {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_generator")
   @SequenceGenerator(name="appointment_generator", sequenceName = "appointment_seq", allocationSize=50, initialValue = 1000)
   private Long id;

   @Column
   private Long examinerId;

   @Column
   private String report;

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

   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn
   private Therapy therapy;
   
   private Period period;

   @Column
   private Boolean isActive;

   @Version
   @Column(nullable = false, columnDefinition = "int default 1")
   private Long version;

   public Appointment() {
   }

   public Appointment(Appointment appointment) {
      this.examinerId = appointment.examinerId;
      this.pharmacy = appointment.pharmacy;
      this.appointmentStatus = appointment.appointmentStatus;
      this.type = appointment.type;
      this.period = appointment.period;
      this.isActive = appointment.isActive;
      this.version = appointment.getVersion();
   }

   public Appointment(AppointmentCancelled appointmentCancelled) {
      examinerId = appointmentCancelled.getExaminerId();
      pharmacy = appointmentCancelled.getPharmacy();
      appointmentStatus = appointmentCancelled.getAppointmentStatus();
      type = appointmentCancelled.getType();
      period = appointmentCancelled.getPeriod();
      patient = appointmentCancelled.getPatient();
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

   public Boolean getActive() {
      return isActive;
   }

   public void setActive(Boolean active) {
      isActive = active;
   }

   public Long getVersion() {
      return version;
   }

   public void setVersion(Long version) {
      this.version = version;
   }

   public boolean isOverlapping(LocalDateTime timeSlot) {
      LocalDateTime start = period.getPeriodStart().minusMinutes(1);
      LocalDateTime end = period.getPeriodEnd().plusMinutes(1);
      return (start.isBefore(timeSlot) && end.isAfter(timeSlot)) ||
              (start.isBefore(timeSlot.plusHours(1)) && end.isAfter(timeSlot.plusHours(1)));
   }
}