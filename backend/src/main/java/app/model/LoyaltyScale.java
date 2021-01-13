package app.model;

import javax.persistence.*;

@Entity
public class LoyaltyScale {

   @Id
   @Enumerated(EnumType.ORDINAL)
   private LoyaltyCategory category;

   @Column
   private int minPoints;

   @Column
   private int maxPoints;

   @Column
   private double discount;

   public LoyaltyScale() {
   }

   public LoyaltyCategory getCategory() {
      return category;
   }

   public void setCategory(LoyaltyCategory category) {
      this.category = category;
   }

   public int getMinPoints() {
      return minPoints;
   }

   public void setMinPoints(int minPoints) {
      this.minPoints = minPoints;
   }

   public int getMaxPoints() {
      return maxPoints;
   }

   public void setMaxPoints(int maxPoints) {
      this.maxPoints = maxPoints;
   }

   public double getDiscount() {
      return discount;
   }

   public void setDiscount(double discount) {
      this.discount = discount;
   }
}