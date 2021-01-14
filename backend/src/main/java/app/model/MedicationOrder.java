package app.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class MedicationOrder {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}