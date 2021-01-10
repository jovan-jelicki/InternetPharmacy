/***********************************************************************
 * Module:  Dermatologist.java
 * Author:  Asus
 * Purpose: Defines the Class Dermatologist
 ***********************************************************************/

import java.util.*;

/** @pdOid 8d0d907d-4118-4e0f-984d-9716af2b3408 */
public class Dermatologist extends User {
   /** @pdRoleInfo migr=no name=WorkingHours assc=association25 coll=java.util.List impl=java.util.ArrayList mult=1..* */
   private java.util.List<WorkingHours> workingHours;
   
   
   /** @pdGenerated default getter */
   public java.util.List<WorkingHours> getWorkingHours() {
      if (workingHours == null)
         workingHours = new java.util.ArrayList<WorkingHours>();
      return workingHours;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorWorkingHours() {
      if (workingHours == null)
         workingHours = new java.util.ArrayList<WorkingHours>();
      return workingHours.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newWorkingHours */
   public void setWorkingHours(java.util.List<WorkingHours> newWorkingHours) {
      removeAllWorkingHours();
      for (java.util.Iterator iter = newWorkingHours.iterator(); iter.hasNext();)
         addWorkingHours((WorkingHours)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newWorkingHours */
   public void addWorkingHours(WorkingHours newWorkingHours) {
      if (newWorkingHours == null)
         return;
      if (this.workingHours == null)
         this.workingHours = new java.util.ArrayList<WorkingHours>();
      if (!this.workingHours.contains(newWorkingHours))
         this.workingHours.add(newWorkingHours);
   }
   
   /** @pdGenerated default remove
     * @param oldWorkingHours */
   public void removeWorkingHours(WorkingHours oldWorkingHours) {
      if (oldWorkingHours == null)
         return;
      if (this.workingHours != null)
         if (this.workingHours.contains(oldWorkingHours))
            this.workingHours.remove(oldWorkingHours);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllWorkingHours() {
      if (workingHours != null)
         workingHours.clear();
   }

}