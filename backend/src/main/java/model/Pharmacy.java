/***********************************************************************
 * Module:  Pharmacy.java
 * Author:  Asus
 * Purpose: Defines the Class Pharmacy
 ***********************************************************************/

import java.util.*;

/** @pdOid 9213dbb0-3584-42cb-9f35-425f9b68c74e */
public class Pharmacy {
   /** @pdOid 1a82ad1e-744a-4274-9127-95d7a929fdbd */
   private String name;
   /** @pdOid 02c91242-d2cf-472c-9190-9e9c3909dbad */
   private String address;
   /** @pdOid 8e3b4214-4bef-4c19-8ecd-51bcb5c4f734 */
   private String description;
   
   /** @pdRoleInfo migr=no name=Dermatologist assc=association8 coll=java.util.Collection impl=java.util.ArrayList mult=0..* */
   private java.util.Collection<Dermatologist> dermatologist;
   /** @pdRoleInfo migr=no name=Pharmacist assc=association9 coll=java.util.Collection impl=java.util.ArrayList mult=0..* */
   private java.util.Collection<Pharmacist> pharmacist;
   /** @pdRoleInfo migr=no name=MedicationQuantity assc=association16 coll=java.util.List impl=java.util.ArrayList mult=0..* */
   private java.util.List<MedicationQuantity> medicationQuantity;
   /** @pdRoleInfo migr=no name=MedicationReservation assc=association19 coll=java.util.List impl=java.util.ArrayList mult=0..* */
   private java.util.List<MedicationReservation> medicationReservation;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Dermatologist> getDermatologist() {
      if (dermatologist == null)
         dermatologist = new java.util.ArrayList<Dermatologist>();
      return dermatologist;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorDermatologist() {
      if (dermatologist == null)
         dermatologist = new java.util.ArrayList<Dermatologist>();
      return dermatologist.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newDermatologist */
   public void setDermatologist(java.util.Collection<Dermatologist> newDermatologist) {
      removeAllDermatologist();
      for (java.util.Iterator iter = newDermatologist.iterator(); iter.hasNext();)
         addDermatologist((Dermatologist)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newDermatologist */
   public void addDermatologist(Dermatologist newDermatologist) {
      if (newDermatologist == null)
         return;
      if (this.dermatologist == null)
         this.dermatologist = new java.util.ArrayList<Dermatologist>();
      if (!this.dermatologist.contains(newDermatologist))
         this.dermatologist.add(newDermatologist);
   }
   
   /** @pdGenerated default remove
     * @param oldDermatologist */
   public void removeDermatologist(Dermatologist oldDermatologist) {
      if (oldDermatologist == null)
         return;
      if (this.dermatologist != null)
         if (this.dermatologist.contains(oldDermatologist))
            this.dermatologist.remove(oldDermatologist);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllDermatologist() {
      if (dermatologist != null)
         dermatologist.clear();
   }
   /** @pdGenerated default getter */
   public java.util.Collection<Pharmacist> getPharmacist() {
      if (pharmacist == null)
         pharmacist = new java.util.ArrayList<Pharmacist>();
      return pharmacist;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorPharmacist() {
      if (pharmacist == null)
         pharmacist = new java.util.ArrayList<Pharmacist>();
      return pharmacist.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newPharmacist */
   public void setPharmacist(java.util.Collection<Pharmacist> newPharmacist) {
      removeAllPharmacist();
      for (java.util.Iterator iter = newPharmacist.iterator(); iter.hasNext();)
         addPharmacist((Pharmacist)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newPharmacist */
   public void addPharmacist(Pharmacist newPharmacist) {
      if (newPharmacist == null)
         return;
      if (this.pharmacist == null)
         this.pharmacist = new java.util.ArrayList<Pharmacist>();
      if (!this.pharmacist.contains(newPharmacist))
         this.pharmacist.add(newPharmacist);
   }
   
   /** @pdGenerated default remove
     * @param oldPharmacist */
   public void removePharmacist(Pharmacist oldPharmacist) {
      if (oldPharmacist == null)
         return;
      if (this.pharmacist != null)
         if (this.pharmacist.contains(oldPharmacist))
            this.pharmacist.remove(oldPharmacist);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllPharmacist() {
      if (pharmacist != null)
         pharmacist.clear();
   }
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
   public java.util.List<MedicationReservation> getMedicationReservation() {
      if (medicationReservation == null)
         medicationReservation = new java.util.ArrayList<MedicationReservation>();
      return medicationReservation;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorMedicationReservation() {
      if (medicationReservation == null)
         medicationReservation = new java.util.ArrayList<MedicationReservation>();
      return medicationReservation.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newMedicationReservation */
   public void setMedicationReservation(java.util.List<MedicationReservation> newMedicationReservation) {
      removeAllMedicationReservation();
      for (java.util.Iterator iter = newMedicationReservation.iterator(); iter.hasNext();)
         addMedicationReservation((MedicationReservation)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newMedicationReservation */
   public void addMedicationReservation(MedicationReservation newMedicationReservation) {
      if (newMedicationReservation == null)
         return;
      if (this.medicationReservation == null)
         this.medicationReservation = new java.util.ArrayList<MedicationReservation>();
      if (!this.medicationReservation.contains(newMedicationReservation))
         this.medicationReservation.add(newMedicationReservation);
   }
   
   /** @pdGenerated default remove
     * @param oldMedicationReservation */
   public void removeMedicationReservation(MedicationReservation oldMedicationReservation) {
      if (oldMedicationReservation == null)
         return;
      if (this.medicationReservation != null)
         if (this.medicationReservation.contains(oldMedicationReservation))
            this.medicationReservation.remove(oldMedicationReservation);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllMedicationReservation() {
      if (medicationReservation != null)
         medicationReservation.clear();
   }

}