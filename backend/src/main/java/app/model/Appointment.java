package app.model;

public class Appointment {

   private Long examinerId;
   private String report;
   private Long pharmacyId;
   private boolean isPatientPresent;
   private Long patientId;
   private EmployeeType type;
   private double cost;
   
   private Therapy therapy;
   private Period period;

}