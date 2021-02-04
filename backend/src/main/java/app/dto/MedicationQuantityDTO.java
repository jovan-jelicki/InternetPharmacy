package app.dto;

public class MedicationQuantityDTO {
    private String medicationName;
    private int medicationQuantity;

    public MedicationQuantityDTO( ) {}

    public MedicationQuantityDTO(String medicationName, int medicationQuantity) {
        this.medicationName = medicationName;
        this.medicationQuantity = medicationQuantity;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public int getMedicationQuantity() {
        return medicationQuantity;
    }

    public void setMedicationQuantity(int medicationQuantity) {
        this.medicationQuantity = medicationQuantity;
    }
}
