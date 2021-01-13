package app.model;

import javax.persistence.*;

@Entity
public class Appointment {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   @JoinColumn
   private User examiner;

   @Column
   private String report;

   @ManyToOne
   @JoinColumn
   private Pharmacy pharmacy;

   @Column
   private boolean isPatientPresent;

   @ManyToOne
   @JoinColumn
   private Patient patient;

   @Enumerated(EnumType.ORDINAL)
   private EmployeeType type;

   @Column
   private double cost;

   @ManyToOne
   @JoinColumn
   private Therapy therapy;
   
   private Period period;

   public Appointment() {
   }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getExaminer() {
      return examiner;
   }

   public void setExaminer(User examiner) {
      this.examiner = examiner;
   }

   public String getReport() {
      return report;
   }

   public void setReport(String report) {
      this.report = report;
   }

   public Pharmacy getPharmacy() {
      return pharmacy;
   }

   public void setPharmacy(Pharmacy pharmacy) {
      this.pharmacy = pharmacy;
   }

   public boolean isPatientPresent() {
      return isPatientPresent;
   }

   public void setPatientPresent(boolean patientPresent) {
      isPatientPresent = patientPresent;
   }

   public Patient getPatient() {
      return patient;
   }

   public void setPatient(Patient patient) {
      this.patient = patient;
   }

   public EmployeeType getType() {
      return type;
   }

   public void setType(EmployeeType type) {
      this.type = type;
   }

   public double getCost() {
      return cost;
   }

   public void setCost(double cost) {
      this.cost = cost;
   }

   public Therapy getTherapy() {
      return therapy;
   }

   public void setTherapy(Therapy therapy) {
      this.therapy = therapy;
   }

   public Period getPeriod() {
      return period;
   }

   public void setPeriod(Period period) {
      this.period = period;
   }
}