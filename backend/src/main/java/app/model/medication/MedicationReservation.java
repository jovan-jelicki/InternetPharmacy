package app.model.medication;

import app.model.user.Patient;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MedicationReservation {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @JoinColumn
   @ManyToOne
   private MedicationQuantity medicationQuantity;

   @Column
   private LocalDateTime pickUpDate;

   @JoinColumn
   @ManyToOne
   private Patient patient;

   @Enumerated(EnumType.ORDINAL)
   private MedicationReservationStatus status;

   public MedicationReservation() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public MedicationQuantity getMedicationQuantity() {
      return medicationQuantity;
   }

   public void setMedicationQuantity(MedicationQuantity medicationQuantity) {
      this.medicationQuantity = medicationQuantity;
   }

   public Patient getPatient() {
      return patient;
   }

   public void setPatient(Patient patient) {
      this.patient = patient;
   }

   public MedicationReservationStatus getStatus() {
      return status;
   }

   public void setStatus(MedicationReservationStatus status) {
      this.status = status;
   }

   public LocalDateTime getPickUpDate() {
      return pickUpDate;
   }

   public void setPickUpDate(LocalDateTime pickUpDate) {
      this.pickUpDate = pickUpDate;
   }
}