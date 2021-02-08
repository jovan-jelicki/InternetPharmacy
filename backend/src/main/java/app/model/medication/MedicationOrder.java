package app.model.medication;

import app.model.user.PharmacyAdmin;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class MedicationOrder {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_order_generator")
   @SequenceGenerator(name="medication_order_generator", sequenceName = "medication_order_seq", allocationSize=50, initialValue = 1000)
   private Long id;

   @Column
   private LocalDateTime deadline;

   @ManyToOne
   @JoinColumn
   private PharmacyAdmin pharmacyAdmin;

   @Enumerated(EnumType.ORDINAL)
   private MedicationOrderStatus status;

   @ManyToMany
   private List<MedicationQuantity> medicationQuantity;

   @Column(nullable = false, columnDefinition = "boolean default true")
   private Boolean isActive;

   @Version
   private Long version;

   public MedicationOrder() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public PharmacyAdmin getPharmacyAdmin() {
      return pharmacyAdmin;
   }

   public void setPharmacyAdmin(PharmacyAdmin pharmacyAdmin) {
      this.pharmacyAdmin = pharmacyAdmin;
   }

   public MedicationOrderStatus getStatus() {
      return status;
   }

   public void setStatus(MedicationOrderStatus status) {
      this.status = status;
   }

   public List<MedicationQuantity> getMedicationQuantity() {
      return medicationQuantity;
   }

   public void setMedicationQuantity(List<MedicationQuantity> medicationQuantity) {
      this.medicationQuantity = medicationQuantity;
   }

   public LocalDateTime getDeadline() {
      return deadline;
   }

   public void setDeadline(LocalDateTime deadline) {
      this.deadline = deadline;
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
}