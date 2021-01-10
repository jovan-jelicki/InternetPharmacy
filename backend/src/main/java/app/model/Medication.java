package app.model;

public class Medication {
   private String code;
   private String name;
   private MedicationType type;
   private int dose;
   private int loyaltyPoints;
   private MedicationShape medicationShape;
   private String manufacturer;
   private MedicationIssue medicationIssue;
   private String note;
   
   private java.util.List<Ingredient> ingredient;
   private java.util.Collection<SideEffect> sideEffect;
   private java.util.Collection<Medication> alternatives;

}