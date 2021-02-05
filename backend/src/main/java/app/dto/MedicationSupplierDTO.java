package app.dto;

public class MedicationSupplierDTO {
    private Long medicationId;
    private int quantity;
    private Long supplierId;

    public MedicationSupplierDTO(){}

    public MedicationSupplierDTO(Long medicationId, int quantity, Long supplierId) {
        this.medicationId = medicationId;
        this.quantity = quantity;
        this.supplierId = supplierId;
    }

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}
