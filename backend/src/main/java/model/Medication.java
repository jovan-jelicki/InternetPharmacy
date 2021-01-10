/***********************************************************************
 * Module:  Medication.java
 * Author:  Asus
 * Purpose: Defines the Class Medication
 ***********************************************************************/

import java.util.*;

/** @pdOid 025eb23e-b9cf-40e7-a5d7-d554847e0071 */
public class Medication {
   /** @pdOid 4ba4bea8-106f-470e-877d-0d40a7ffc65e */
   private String code;
   /** @pdOid 010b938a-6a65-415e-9338-3cc0b3469f52 */
   private String name;
   /** @pdOid 26f779e7-e3fd-4920-9a13-9041fd4fb3e5 */
   private MedicationType type;
   /** @pdOid 17266de1-6230-47b4-b6a0-59cbea803fc3 */
   private int dose;
   /** @pdOid 93d28457-b512-40c5-9561-1b26c5eab8c4 */
   private int loyaltyPoints;
   /** @pdOid 02c8b616-de22-40d2-ac98-badbc43f9d85 */
   private MedicationShape medicationShape;
   /** @pdOid 99529d88-fedd-45fd-8cf6-26fb2777c77d */
   private String manufacturer;
   /** @pdOid 61140f38-5ee0-406a-a172-cea1954913f2 */
   private MedicationIssue medicationIssue;
   /** @pdOid 27280504-ee25-4442-876c-aa9293cb160d */
   private String note;
   
   /** @pdRoleInfo migr=no name=Ingredient assc=association11 coll=java.util.List impl=java.util.ArrayList mult=1..* */
   private java.util.List<Ingredient> ingredient;
   /** @pdRoleInfo migr=no name=SideEffect assc=association12 coll=java.util.Collection impl=java.util.ArrayList mult=0..* */
   private java.util.Collection<SideEffect> sideEffect;
   /** @pdRoleInfo migr=no name=Medication assc=association13 coll=java.util.Collection impl=java.util.HashSet mult=0..* */
   private java.util.Collection<Medication> alternatives;
   
   
   /** @pdGenerated default getter */
   public java.util.List<Ingredient> getIngredient() {
      if (ingredient == null)
         ingredient = new java.util.ArrayList<Ingredient>();
      return ingredient;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorIngredient() {
      if (ingredient == null)
         ingredient = new java.util.ArrayList<Ingredient>();
      return ingredient.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newIngredient */
   public void setIngredient(java.util.List<Ingredient> newIngredient) {
      removeAllIngredient();
      for (java.util.Iterator iter = newIngredient.iterator(); iter.hasNext();)
         addIngredient((Ingredient)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newIngredient */
   public void addIngredient(Ingredient newIngredient) {
      if (newIngredient == null)
         return;
      if (this.ingredient == null)
         this.ingredient = new java.util.ArrayList<Ingredient>();
      if (!this.ingredient.contains(newIngredient))
         this.ingredient.add(newIngredient);
   }
   
   /** @pdGenerated default remove
     * @param oldIngredient */
   public void removeIngredient(Ingredient oldIngredient) {
      if (oldIngredient == null)
         return;
      if (this.ingredient != null)
         if (this.ingredient.contains(oldIngredient))
            this.ingredient.remove(oldIngredient);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllIngredient() {
      if (ingredient != null)
         ingredient.clear();
   }
   /** @pdGenerated default getter */
   public java.util.Collection<SideEffect> getSideEffect() {
      if (sideEffect == null)
         sideEffect = new java.util.ArrayList<SideEffect>();
      return sideEffect;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorSideEffect() {
      if (sideEffect == null)
         sideEffect = new java.util.ArrayList<SideEffect>();
      return sideEffect.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newSideEffect */
   public void setSideEffect(java.util.Collection<SideEffect> newSideEffect) {
      removeAllSideEffect();
      for (java.util.Iterator iter = newSideEffect.iterator(); iter.hasNext();)
         addSideEffect((SideEffect)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newSideEffect */
   public void addSideEffect(SideEffect newSideEffect) {
      if (newSideEffect == null)
         return;
      if (this.sideEffect == null)
         this.sideEffect = new java.util.ArrayList<SideEffect>();
      if (!this.sideEffect.contains(newSideEffect))
         this.sideEffect.add(newSideEffect);
   }
   
   /** @pdGenerated default remove
     * @param oldSideEffect */
   public void removeSideEffect(SideEffect oldSideEffect) {
      if (oldSideEffect == null)
         return;
      if (this.sideEffect != null)
         if (this.sideEffect.contains(oldSideEffect))
            this.sideEffect.remove(oldSideEffect);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllSideEffect() {
      if (sideEffect != null)
         sideEffect.clear();
   }
   /** @pdGenerated default getter */
   public java.util.Collection<Medication> getAlternatives() {
      if (alternatives == null)
         alternatives = new java.util.HashSet<Medication>();
      return alternatives;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorAlternatives() {
      if (alternatives == null)
         alternatives = new java.util.HashSet<Medication>();
      return alternatives.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newAlternatives */
   public void setAlternatives(java.util.Collection<Medication> newAlternatives) {
      removeAllAlternatives();
      for (java.util.Iterator iter = newAlternatives.iterator(); iter.hasNext();)
         addAlternatives((Medication)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newMedication */
   public void addAlternatives(Medication newMedication) {
      if (newMedication == null)
         return;
      if (this.alternatives == null)
         this.alternatives = new java.util.HashSet<Medication>();
      if (!this.alternatives.contains(newMedication))
         this.alternatives.add(newMedication);
   }
   
   /** @pdGenerated default remove
     * @param oldMedication */
   public void removeAlternatives(Medication oldMedication) {
      if (oldMedication == null)
         return;
      if (this.alternatives != null)
         if (this.alternatives.contains(oldMedication))
            this.alternatives.remove(oldMedication);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllAlternatives() {
      if (alternatives != null)
         alternatives.clear();
   }

}