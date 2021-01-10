/***********************************************************************
 * Module:  PharmacyAdmin.java
 * Author:  Asus
 * Purpose: Defines the Class PharmacyAdmin
 ***********************************************************************/

import java.util.*;

/** @pdOid 1d444531-797a-4781-87d6-ae3b74e834ce */
public class PharmacyAdmin extends User {
   /** @pdRoleInfo migr=no name=Pharmacy assc=association4 mult=1..1 */
   private Pharmacy pharmacy;

}