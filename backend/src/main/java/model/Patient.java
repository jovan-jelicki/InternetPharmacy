/***********************************************************************
 * Module:  Patient.java
 * Author:  Asus
 * Purpose: Defines the Class Patient
 ***********************************************************************/

import java.util.*;

/** @pdOid 3788c75c-6278-4b79-9d89-ac8d57a29438 */
public class Patient extends User {
   /** @pdOid a246b54a-5131-4ebe-9fc5-ce14e6c876f5 */
   private int penaltyCount;
   
   /** @pdRoleInfo migr=no name=Ingredient assc=association3 coll=java.util.List impl=java.util.ArrayList mult=0..* */
   private java.util.List<Ingredient> allergies;
   /** @pdRoleInfo migr=no name=Promotion assc=association21 mult=0..* side=A */
   private Promotion[] promotion;
   
   
   /** @pdGenerated default getter */
   public java.util.List<Ingredient> getAllergies() {
      if (allergies == null)
         allergies = new java.util.ArrayList<Ingredient>();
      return allergies;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorAllergies() {
      if (allergies == null)
         allergies = new java.util.ArrayList<Ingredient>();
      return allergies.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newAllergies */
   public void setAllergies(java.util.List<Ingredient> newAllergies) {
      removeAllAllergies();
      for (java.util.Iterator iter = newAllergies.iterator(); iter.hasNext();)
         addAllergies((Ingredient)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newIngredient */
   public void addAllergies(Ingredient newIngredient) {
      if (newIngredient == null)
         return;
      if (this.allergies == null)
         this.allergies = new java.util.ArrayList<Ingredient>();
      if (!this.allergies.contains(newIngredient))
         this.allergies.add(newIngredient);
   }
   
   /** @pdGenerated default remove
     * @param oldIngredient */
   public void removeAllergies(Ingredient oldIngredient) {
      if (oldIngredient == null)
         return;
      if (this.allergies != null)
         if (this.allergies.contains(oldIngredient))
            this.allergies.remove(oldIngredient);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllAllergies() {
      if (allergies != null)
         allergies.clear();
   }

}