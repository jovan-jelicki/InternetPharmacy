package app.dto;

import app.model.medication.MedicationOrderStatus;
import app.model.medication.MedicationQuantity;

import java.time.LocalDateTime;
import java.util.List;

public class MedicationOrderSupplierDTO {
    private Long medicationOrderId;
    private String pharmacyName;
    private LocalDateTime deadline;
    private List<MedicationQuantity> medicationQuantity;
    private MedicationOrderStatus status;

    public MedicationOrderSupplierDTO(){}

    public MedicationOrderSupplierDTO(Long medicationOrderId, String pharmacyName, LocalDateTime deadLine, List<MedicationQuantity> medicationQuantity, MedicationOrderStatus status) {
        this.medicationOrderId = medicationOrderId;
        this.pharmacyName = pharmacyName;
        this.deadline = deadLine;
        this.medicationQuantity = medicationQuantity;
        this.status = status;
    }

    public Long getMedicationOrderId() {
        return medicationOrderId;
    }

    public void setMedicationOrderId(Long medicationOrderId) {
        this.medicationOrderId = medicationOrderId;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public List<MedicationQuantity> getMedicationQuantity() {
        return medicationQuantity;
    }

    public void setMedicationQuantity(List<MedicationQuantity> medicationQuantity) {
        this.medicationQuantity = medicationQuantity;
    }

    public MedicationOrderStatus getStatus() {
        return status;
    }

    public void setStatus(MedicationOrderStatus status) {
        this.status = status;
    }
}
