/***********************************************************************
 * Module:  EPrescription.java
 * Author:  Asus
 * Purpose: Defines the Class EPrescription
 ***********************************************************************/

import java.util.*;

/** @pdOid 111c0f30-f0ab-4340-9d9e-5e202125b471 */
public class EPrescription {
   /** @pdOid 59e45a5b-a34b-4a68-80d9-90d0cca7ff6e */
   private String code;
   /** @pdOid 457b7db1-274b-436a-b301-c7db8e959a28 */
   private java.util.Date dateIssued;
   /** @pdOid 2f711aa4-5190-467c-b2b6-48f26be60a63 */
   private Long patientId;
   
   /** @pdRoleInfo migr=no name=MedicationQuantity assc=association18 coll=java.util.List impl=java.util.ArrayList mult=0..* */
   private java.util.List<MedicationQuantity> medicationQuantity;
   
   
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

}