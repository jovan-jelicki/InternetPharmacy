package app.dto;

public class GetMedicationReservationDTO {
    private Long pharmacistId;
    private Long medicationId;

    public GetMedicationReservationDTO(Long pharmacistId, Long medicationId) {
        this.pharmacistId = pharmacistId;
        this.medicationId = medicationId;
    }

    public GetMedicationReservationDTO() {
    }

    public Long getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(Long pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }
}
