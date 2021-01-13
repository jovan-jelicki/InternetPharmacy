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

}