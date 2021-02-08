package app.model.medication;

import app.model.user.Patient;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MedicationReservation {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_reservation_generator")
   @SequenceGenerator(name="medication_reservation_generator", sequenceName = "medication_reservation_seq", allocationSize=50, initialValue = 1000)
   private Long id;

   @JoinColumn
   @ManyToOne(cascade = CascadeType.ALL)
   private MedicationQuantity medicationQuantity;

   @Column
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime pickUpDate;

   //dodaj kolicinu popusta
   @Column(columnDefinition = "boolean default false")
   private Boolean isDiscounted;

   @JoinColumn
   @ManyToOne(cascade = CascadeType.ALL)
   private Patient patient;

   @Enumerated(EnumType.ORDINAL)
   private MedicationReservationStatus status;

   @Version
   private Long version;

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

   public Boolean getDiscounted() {
      return isDiscounted;
   }

   public void setDiscounted(Boolean discounted) {
      isDiscounted = discounted;
   }

   public Long getVersion() {
      return version;
   }

   public void setVersion(Long version) {
      this.version = version;
   }
}