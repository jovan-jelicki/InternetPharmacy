package app.dto;

import app.model.medication.EPrescription;
import app.model.user.EmployeeType;

public class MakeEPrescriptionDTO {
    private EPrescription prescription;
    private Long pharmacyId;
    private Long examinerId;
    private EmployeeType employeeType;

    public MakeEPrescriptionDTO() {
    }

    public Long getExaminerId() {
        return examinerId;
    }

    public void setExaminerId(Long examinerId) {
        this.examinerId = examinerId;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public EPrescription getPrescription() {
        return prescription;
    }

    public void setPrescription(EPrescription prescription) {
        this.prescription = prescription;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }
}
