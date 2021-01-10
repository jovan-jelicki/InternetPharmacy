/***********************************************************************
 * Module:  Pharmacist.java
 * Author:  Asus
 * Purpose: Defines the Class Pharmacist
 ***********************************************************************/

import java.util.*;

/** @pdOid 7e8c4cd8-7fe1-4cc7-8b8a-266dc317db8c */
public class Pharmacist extends User {
   /** @pdRoleInfo migr=no name=WorkingHours assc=association24 mult=0..1 */
   private WorkingHours workingHours;

}