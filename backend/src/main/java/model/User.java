/***********************************************************************
 * Module:  User.java
 * Author:  Asus
 * Purpose: Defines the Class User
 ***********************************************************************/

import java.util.*;

/** @pdOid b4e2d129-67d4-40e9-b9c0-5161f6b7bbdb */
public class User {
   /** @pdOid 1dc8c813-d199-4bb4-bbba-b07859009520 */
   private String firstName;
   /** @pdOid cb178a9c-4211-4e14-b651-f965cf565e28 */
   private String lastName;
   
   /** @pdRoleInfo migr=no name=Credencials assc=association1 mult=0..1 */
   private Credencials credencials;
   /** @pdRoleInfo migr=no name=Contact assc=association2 mult=0..1 */
   private Contact contact;

}