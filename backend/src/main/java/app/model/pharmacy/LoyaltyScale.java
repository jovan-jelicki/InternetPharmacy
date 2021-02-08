package app.model.pharmacy;

import javax.persistence.*;

@Entity
public class LoyaltyScale {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loyalty_scale_generator")
   @SequenceGenerator(name="loyalty_scale_generator", sequenceName = "loyalty_scale_seq", allocationSize=50, initialValue = 1000)
   private Long id;

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

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }
}