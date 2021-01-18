package app.model.medication;

import app.model.time.Period;
import app.model.pharmacy.Pharmacy;

import javax.persistence.*;

@Entity
public class MedicationPriceList {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   Long id;

   @JoinColumn
   @ManyToOne
   private Medication medication;

   @Column
   private double cost;

   private Period period;

   @JoinColumn
   @OneToOne
   private Pharmacy pharmacy;

   public MedicationPriceList() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Medication getMedication() {
      return medication;
   }

   public void setMedication(Medication medication) {
      this.medication = medication;
   }

   public double getCost() {
      return cost;
   }

   public void setCost(double cost) {
      this.cost = cost;
   }

   public Period getPeriod() {
      return period;
   }

   public void setPeriod(Period period) {
      this.period = period;
   }

   public Pharmacy getPharmacy() {
      return pharmacy;
   }

   public void setPharmacy(Pharmacy pharmacy) {
      this.pharmacy = pharmacy;
   }
}