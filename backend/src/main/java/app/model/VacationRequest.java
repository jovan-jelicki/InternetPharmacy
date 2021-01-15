package app.model;

import javax.persistence.*;

@Entity
public class VacationRequest {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column
   private Long employeeId;

   private Period period;

   @Column
   private boolean isApproved;

   @ManyToOne
   private Pharmacy pharmacy;

   @Column
   private String rejectionNote;

   @Column
   private String vacationNote;

   @Enumerated(EnumType.ORDINAL)
   private EmployeeType employeeType;

   public VacationRequest() {
   }

   public String getVacationNote() {
      return vacationNote;
   }

   public void setVacationNote(String vacationNote) {
      this.vacationNote = vacationNote;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getEmployeeId() {
      return employeeId;
   }

   public void setEmployeeId(Long empoyeeId) {
      this.employeeId = empoyeeId;
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