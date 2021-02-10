package app.model.medication;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MedicationOffer {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_offer_generator")
   @SequenceGenerator(name="medication_offer_generator", sequenceName = "medication_offer_seq", allocationSize=50, initialValue = 1000)
   private Long id;
   @Column
   private double cost;

   @Column
   private LocalDateTime shippingDate;

   @ManyToOne
   private MedicationOrder medicationOrder;

   @Enumerated(EnumType.ORDINAL)
   private MedicationOfferStatus status;

   @Version
   @Column(nullable = false, columnDefinition = "int default 1")
   private Long version;

   public MedicationOffer() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public double getCost() {
      return cost;
   }

   public void setCost(double cost) {
      this.cost = cost;
   }

   public MedicationOrder getMedicationOrder() {
      return medicationOrder;
   }

   public void setMedicationOrder(MedicationOrder medicationOrder) {
      this.medicationOrder = medicationOrder;
   }

   public MedicationOfferStatus getStatus() {
      return status;
   }

   public void setStatus(MedicationOfferStatus status) {
      this.status = status;
   }

   public LocalDateTime getShippingDate() {
      return shippingDate;
   }

   public void setShippingDate(LocalDateTime shippingDate) {
      this.shippingDate = shippingDate;
   }

   public Long getVersion() {
      return version;
   }

   public void setVersion(Long version) {
      this.version = version;
   }
}