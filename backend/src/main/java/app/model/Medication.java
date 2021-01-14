package app.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Medication {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column
   private String name;

   @Enumerated(EnumType.ORDINAL)
   private MedicationType type;

   @Column
   private int dose;

   @Column
   private int loyaltyPoints;

   @Enumerated(EnumType.ORDINAL)
   private MedicationShape medicationShape;

   @Column
   private String manufacturer;

   @Column
   private MedicationIssue medicationIssue;

   @Column
   private String note;

   @ManyToMany
   private List<Ingredient> ingredient;

   @ManyToMany
   private List<SideEffect> sideEffect;

   @ManyToMany
   private List<Medication> alternatives;

   public Medication() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public MedicationType getType() {
      return type;
   }

   public void setType(MedicationType type) {
      this.type = type;
   }

   public int getDose() {
      return dose;
   }

   public void setDose(int dose) {
      this.dose = dose;
   }

   public int getLoyaltyPoints() {
      return loyaltyPoints;
   }

   public void setLoyaltyPoints(int loyaltyPoints) {
      this.loyaltyPoints = loyaltyPoints;
   }

   public MedicationShape getMedicationShape() {
      return medicationShape;
   }

   public void setMedicationShape(MedicationShape medicationShape) {
      this.medicationShape = medicationShape;
   }

   public String getManufacturer() {
      return manufacturer;
   }

   public void setManufacturer(String manufacturer) {
      this.manufacturer = manufacturer;
   }

   public MedicationIssue getMedicationIssue() {
      return medicationIssue;
   }

   public void setMedicationIssue(MedicationIssue medicationIssue) {
      this.medicationIssue = medicationIssue;
   }

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
   }

   public List<Ingredient> getIngredient() {
      return ingredient;
   }

   public void setIngredient(List<Ingredient> ingredient) {
      this.ingredient = ingredient;
   }

   public List<SideEffect> getSideEffect() {
      return sideEffect;
   }

   public void setSideEffect(List<SideEffect> sideEffect) {
      this.sideEffect = sideEffect;
   }

   public List<Medication> getAlternatives() {
      return alternatives;
   }

   public void setAlternatives(List<Medication> alternatives) {
      this.alternatives = alternatives;
   }
}