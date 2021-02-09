package app.dto;

import java.util.List;

public class MedicationQRDTO {
    private List<String> medicationId;

    public MedicationQRDTO() {
    }
    public MedicationQRDTO(List<String> medicationId) {
        this.medicationId = medicationId;
    }

    public List<String> getMedicationIds() {
        return medicationId;
    }

    public void setMedicationIds(List<String> medicationId) {
        this.medicationId = medicationId;
    }
}
