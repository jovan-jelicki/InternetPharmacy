package app.model.medication;

import app.dto.MedicationSearchDTO;
import app.dto.PharmacySearchDTO;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Medication {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_generator")
   @SequenceGenerator(name="medication_generator", sequenceName = "medication_seq", allocationSize=50, initialValue = 1000)
   private Long id;

   @Column
   private String name;

   @Enumerated(EnumType.ORDINAL)
   private MedicationType type;

   @Column
   private double dose;

   @Column
   private int loyaltyPoints;

   @Enumerated(EnumType.ORDINAL)
   private MedicationShape medicationShape;

   @Column
   private String manufacturer;

   @Column
   private MedicationIssue medicationIssue;

   @Column(columnDefinition = "TEXT")
   private String note;

   @ManyToMany(fetch = FetchType.EAGER)
   private Set<Ingredient> ingredient;

   @ManyToMany(fetch = FetchType.EAGER)
   private Set<SideEffect> sideEffect;

   @ManyToMany(fetch = FetchType.LAZY)
   private Set<Medication> alternatives;

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

   public double getDose() {
      return dose;
   }

   public void setDose(double dose) {
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

   public Set<Ingredient> getIngredient() {
      return ingredient;
   }

   public void setIngredient(Set<Ingredient> ingredient) {
      this.ingredient = ingredient;
   }

   public Set<SideEffect> getSideEffect() {
      return sideEffect;
   }

   public void setSideEffect(Set<SideEffect> sideEffect) {
      this.sideEffect = sideEffect;
   }

   public Set<Medication> getAlternatives() {
      return alternatives;
   }

   public void setAlternatives(Set<Medication> alternatives) {
      this.alternatives = alternatives;
   }

   public boolean isEqual(MedicationSearchDTO medicationSearchDTO) {
      return searchCondition(medicationSearchDTO.getName(), name);
   }

   private boolean searchCondition(String searched, String actual) {
      if(searched.trim().isEmpty())
         return true;
      else
         return actual.toLowerCase().contains(searched.toLowerCase());
   }
}