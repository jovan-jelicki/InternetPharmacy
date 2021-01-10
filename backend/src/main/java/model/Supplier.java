/***********************************************************************
 * Module:  Supplier.java
 * Author:  Asus
 * Purpose: Defines the Class Supplier
 ***********************************************************************/

import java.util.*;

/** @pdOid e843fd39-dfb8-4a9b-9978-761a3307d1ae */
public class Supplier extends User {
   /** @pdRoleInfo migr=no name=MedicationQuantity assc=association22 coll=java.util.List impl=java.util.ArrayList mult=0..* */
   private java.util.List<MedicationQuantity> medicationQuantity;
   /** @pdRoleInfo migr=no name=MedicationOffer assc=association15 coll=java.util.List impl=java.util.ArrayList mult=0..* */
   private java.util.List<MedicationOffer> medicationOffer;
   
   
   /** @pdGenerated default getter */
   public java.util.List<MedicationQuantity> getMedicationQuantity() {
      if (medicationQuantity == null)
         medicationQuantity = new java.util.ArrayList<MedicationQuantity>();
      return medicationQuantity;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorMedicationQuantity() {
      if (medicationQuantity == null)
         medicationQuantity = new java.util.ArrayList<MedicationQuantity>();
      return medicationQuantity.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newMedicationQuantity */
   public void setMedicationQuantity(java.util.List<MedicationQuantity> newMedicationQuantity) {
      removeAllMedicationQuantity();
      for (java.util.Iterator iter = newMedicationQuantity.iterator(); iter.hasNext();)
         addMedicationQuantity((MedicationQuantity)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newMedicationQuantity */
   public void addMedicationQuantity(MedicationQuantity newMedicationQuantity) {
      if (newMedicationQuantity == null)
         return;
      if (this.medicationQuantity == null)
         this.medicationQuantity = new java.util.ArrayList<MedicationQuantity>();
      if (!this.medicationQuantity.contains(newMedicationQuantity))
         this.medicationQuantity.add(newMedicationQuantity);
   }
   
   /** @pdGenerated default remove
     * @param oldMedicationQuantity */
   public void removeMedicationQuantity(MedicationQuantity oldMedicationQuantity) {
      if (oldMedicationQuantity == null)
         return;
      if (this.medicationQuantity != null)
         if (this.medicationQuantity.contains(oldMedicationQuantity))
            this.medicationQuantity.remove(oldMedicationQuantity);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllMedicationQuantity() {
      if (medicationQuantity != null)
         medicationQuantity.clear();
   }
   /** @pdGenerated default getter */
   public java.util.List<MedicationOffer> getMedicationOffer() {
      if (medicationOffer == null)
         medicationOffer = new java.util.ArrayList<MedicationOffer>();
      return medicationOffer;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorMedicationOffer() {
      if (medicationOffer == null)
         medicationOffer = new java.util.ArrayList<MedicationOffer>();
      return medicationOffer.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newMedicationOffer */
   public void setMedicationOffer(java.util.List<MedicationOffer> newMedicationOffer) {
      removeAllMedicationOffer();
      for (java.util.Iterator iter = newMedicationOffer.iterator(); iter.hasNext();)
         addMedicationOffer((MedicationOffer)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newMedicationOffer */
   public void addMedicationOffer(MedicationOffer newMedicationOffer) {
      if (newMedicationOffer == null)
         return;
      if (this.medicationOffer == null)
         this.medicationOffer = new java.util.ArrayList<MedicationOffer>();
      if (!this.medicationOffer.contains(newMedicationOffer))
         this.medicationOffer.add(newMedicationOffer);
   }
   
   /** @pdGenerated default remove
     * @param oldMedicationOffer */
   public void removeMedicationOffer(MedicationOffer oldMedicationOffer) {
      if (oldMedicationOffer == null)
         return;
      if (this.medicationOffer != null)
         if (this.medicationOffer.contains(oldMedicationOffer))
            this.medicationOffer.remove(oldMedicationOffer);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllMedicationOffer() {
      if (medicationOffer != null)
         medicationOffer.clear();
   }

}