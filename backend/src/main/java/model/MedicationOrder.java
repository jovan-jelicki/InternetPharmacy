/***********************************************************************
 * Module:  MedicationOrder.java
 * Author:  Asus
 * Purpose: Defines the Class MedicationOrder
 ***********************************************************************/

import java.util.*;

/** @pdOid 23ea5370-8427-4903-98a5-f8a23a9ca478 */
public class MedicationOrder {
   /** @pdOid 91a008e7-54f3-49ee-ac9a-98a39c1243fa */
   private java.util.Date deadline;
   /** @pdOid 42ea8532-0e14-4c68-b7f1-9794574b7542 */
   private Long adminId;
   /** @pdOid 241e7973-adb9-452a-aa21-f50e18099514 */
   private MedicationOrderStatus status;
   
   /** @pdRoleInfo migr=no name=MedicationQuantity assc=association14 coll=java.util.List impl=java.util.ArrayList mult=1..* */
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