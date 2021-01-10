/***********************************************************************
 * Module:  Appointment.java
 * Author:  Asus
 * Purpose: Defines the Class Appointment
 ***********************************************************************/

import java.util.*;

/** @pdOid 7ce0aa69-924b-4bb9-9f8f-4dceb606ec3f */
public class Appointment {
   /** @pdOid 219302fd-c710-4629-8d39-001f06da15ca */
   private Long examinerId;
   /** @pdOid d0f47088-01bb-417c-b7eb-9526fe7917c2 */
   private String report;
   /** @pdOid 3797c494-da1c-4252-9fa7-f18c232f8758 */
   private Long pharmacyId;
   /** @pdOid f70e0728-ccd1-4bcc-90d7-f8ec3156ddda */
   private boolean isPatientPresent;
   /** @pdOid d04efb12-5303-4f92-a339-43c94f715043 */
   private Long patientId;
   /** @pdOid 55191597-9187-40ff-9f1e-e1a2b0da86a5 */
   private EmployeeType type;
   /** @pdOid 5bf94026-28a6-412c-9146-560dfb3ee91e */
   private double cost;
   
   /** @pdRoleInfo migr=no name=Therapy assc=association23 mult=0..1 */
   private Therapy therapy;
   /** @pdRoleInfo migr=no name=Period assc=association10 mult=0..1 */
   private Period period;

}