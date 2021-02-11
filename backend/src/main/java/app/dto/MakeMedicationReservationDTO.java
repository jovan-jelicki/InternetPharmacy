package app.dto;

import app.model.medication.MedicationReservation;

public class MakeMedicationReservationDTO {
    private MedicationReservation medicationReservation;
    private Long pharmacyId;

    public MakeMedicationReservationDTO() {
    }

    public MakeMedicationReservationDTO(MedicationReservation medicationReservation, Long pharmacyId) {
        this.medicationReservation = medicationReservation;
        this.pharmacyId = pharmacyId;
    }

    public MedicationReservation getMedicationReservation() {
        return medicationReservation;
    }

    public void setMedicationReservation(MedicationReservation medicationReservation) {
        this.medicationReservation = medicationReservation;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }
}
