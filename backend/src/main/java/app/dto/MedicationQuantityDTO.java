package app.dto;

public class MedicationQuantityDTO {
    private Long medicationId;
    private String medicationName;
    private Long medicationQuantity;

    public MedicationQuantityDTO( ) {}

    public MedicationQuantityDTO(String medicationName, Long medicationQuantity, Long medicationId) {
        this.medicationName = medicationName;
        this.medicationQuantity = medicationQuantity;
        this.medicationId=medicationId;
    }

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public Long getMedicationQuantity() {
        return medicationQuantity;
    }

    public void setMedicationQuantity(Long medicationQuantity) {
        this.medicationQuantity = medicationQuantity;
    }
}
