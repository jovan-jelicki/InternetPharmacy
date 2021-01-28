package app.dto;

import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.model.time.VacationRequest;
import app.model.time.VacationRequestStatus;
import app.model.user.EmployeeType;

public class VacationRequestSendDTO {
    private Period period;
    private Long employeeId;
    private EmployeeType employeeType;
    private String vacationNote;
    private VacationRequestStatus vacationRequestStatus;
    private Pharmacy pharmacy;

    public VacationRequestSendDTO(VacationRequest vacationRequest) {
        this.period = vacationRequest.getPeriod();
        this.employeeId = vacationRequest.getEmployeeId();
        this.employeeType = vacationRequest.getEmployeeType();
        this.vacationNote = vacationRequest.getVacationNote();
        this.vacationRequestStatus = vacationRequest.getVacationRequestStatus();
        this.pharmacy = vacationRequest.getPharmacy();
    }

    public VacationRequestSendDTO() {
    }

    public VacationRequest createEntity(){
        VacationRequest vacationRequest = new VacationRequest();
        vacationRequest.setPeriod(this.period);
        vacationRequest.setEmployeeId(this.employeeId);
        vacationRequest.setEmployeeType(this.employeeType);
        vacationRequest.setVacationNote(this.vacationNote);
        vacationRequest.setVacationRequestStatus(this.vacationRequestStatus);
        return vacationRequest;
    }
    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public String getVacationNote() {
        return vacationNote;
    }

    public void setVacationNote(String vacationNote) {
        this.vacationNote = vacationNote;
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
}
