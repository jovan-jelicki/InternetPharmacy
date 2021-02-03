package app.dto;

import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.model.time.VacationRequest;
import app.model.time.VacationRequestStatus;
import app.model.user.EmployeeType;
import app.model.user.User;

public class VacationRequestDTO {
    private Long id;
    private Long employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private String employeeEmail;
    private Period period;
    private VacationRequestStatus vacationRequestStatus;
    private Pharmacy pharmacy;
    private String rejectionNote;
    private String vacationNote;
    private EmployeeType employeeType;

    public VacationRequestDTO() {
    }

    public VacationRequestDTO(User user, VacationRequest vacationRequest) {
        this.id = vacationRequest.getId();
        this.employeeId = vacationRequest.getEmployeeId();
        this.employeeFirstName = user.getFirstName();
        this.employeeLastName = user.getLastName();
        this.period = vacationRequest.getPeriod();
        this.vacationRequestStatus = vacationRequest.getVacationRequestStatus();
        this.pharmacy = vacationRequest.getPharmacy();
        this.pharmacy.setMedicationQuantity(null);
        this.pharmacy.setMedicationReservation(null);
        this.rejectionNote = vacationRequest.getRejectionNote();
        this.vacationNote = vacationRequest.getVacationNote();
        this.employeeType = vacationRequest.getEmployeeType();
        this.employeeEmail = user.getCredentials().getEmail();
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

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
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

    public String getVacationNote() {
        return vacationNote;
    }

    public void setVacationNote(String vacationNote) {
        this.vacationNote = vacationNote;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }
}
