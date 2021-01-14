package app.model;

import javax.persistence.*;

@Entity
public class VacationRequest {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column
   private Long empoyeeId;

   private Period period;

   @Column
   private boolean isApproved;

   @ManyToOne
   private Pharmacy pharmacy;

   @Column
   private String rejectionNote;

   @Enumerated(EnumType.ORDINAL)
   private EmployeeType employeeType;

   public VacationRequest() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getEmpoyeeId() {
      return empoyeeId;
   }

   public void setEmpoyeeId(Long empoyeeId) {
      this.empoyeeId = empoyeeId;
   }

   public Period getPeriod() {
      return period;
   }

   public void setPeriod(Period period) {
      this.period = period;
   }

   public boolean isApproved() {
      return isApproved;
   }

   public void setApproved(boolean approved) {
      isApproved = approved;
   }

   public Pharmacy getPharmacy() {
      return pharmacy;
   }

   public void setPharmacy(Pharmacy pharmacy) {
      this.pharmacy = pharmacy;
   }

   public String getRejectionNote() {
      return rejectionNote;
   }

   public void setRejectionNote(String rejectionNote) {
      this.rejectionNote = rejectionNote;
   }

   public EmployeeType getEmployeeType() {
      return employeeType;
   }

   public void setEmployeeType(EmployeeType employeeType) {
      this.employeeType = employeeType;
   }
}