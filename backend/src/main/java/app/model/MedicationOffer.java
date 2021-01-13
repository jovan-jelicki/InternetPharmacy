package app.model;

import javax.persistence.*;

@Entity
public class MedicationOffer {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column
   private double cost;
//   private java.util.Date shippingDate;
   @ManyToOne
   private MedicationOrder medicationOrder;

   @Enumerated(EnumType.ORDINAL)
   private MedicationOfferStatus status;

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
}