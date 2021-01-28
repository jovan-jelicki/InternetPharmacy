package app.model.time;

import app.model.pharmacy.Pharmacy;
import app.model.user.EmployeeType;

import javax.persistence.*;

@Entity
public class VacationRequest {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacation_generator")
   @SequenceGenerator(name="vacation_generator", sequenceName = "vacation_seq", allocationSize=50, initialValue = 1000)
   private Long id;

   @Column
   private Long employeeId;

   private Period period;

   @Enumerated(EnumType.ORDINAL)
   private VacationRequestStatus vacationRequestStatus;

   @ManyToOne
   private Pharmacy pharmacy;

   @Column(columnDefinition = "TEXT")
   private String rejectionNote;

   @Column(columnDefinition = "TEXT")
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

   public VacationRequestStatus getVacationRequestStatus() {
      return vacationRequestStatus;
   }

   public void setVacationRequestStatus(VacationRequestStatus vacationRequestStatus) {
      this.vacationRequestStatus = vacationRequestStatus;
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