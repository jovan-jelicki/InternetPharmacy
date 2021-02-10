package app.dto;

public class PharmacyPrescriptionDTO {
    private Long pharmacyId;
    private Long prescriptionId;

    public PharmacyPrescriptionDTO(Long pharmacyId, Long prescriptionId) {
        this.pharmacyId = pharmacyId;
        this.prescriptionId = prescriptionId;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
}
