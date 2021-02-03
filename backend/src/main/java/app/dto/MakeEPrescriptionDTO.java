package app.dto;

import app.model.medication.EPrescription;

public class MakeEPrescriptionDTO {
    private EPrescription prescription;
    private Long pharmacyId;

    public MakeEPrescriptionDTO() {
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
