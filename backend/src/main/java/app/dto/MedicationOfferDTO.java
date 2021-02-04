package app.dto;

import app.model.medication.MedicationOfferStatus;

import java.time.LocalDateTime;

public class MedicationOfferDTO {
    private Long id;
    private double cost;
    private LocalDateTime shippingDate;
    private MedicationOfferStatus status;
    private Long medicationOrderId;
    private Long supplierId;

    public MedicationOfferDTO(){}
    public MedicationOfferDTO(Long id, double cost, LocalDateTime shippingDate, MedicationOfferStatus status,Long medicationOrderId,Long supplierId) {
        this.id = id;
        this.cost = cost;
        this.shippingDate = shippingDate;
        this.status = status;
        this.medicationOrderId =medicationOrderId;
        this.supplierId=supplierId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }


    public Long getMedicationOrderId() {
        return medicationOrderId;
    }

    public void setMedicationOrderId(Long medicationOrderId) {
        this.medicationOrderId = medicationOrderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDateTime getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDateTime shippingDate) {
        this.shippingDate = shippingDate;
    }

    public MedicationOfferStatus getStatus() {
        return status;
    }

    public void setStatus(MedicationOfferStatus status) {
        this.status = status;
    }
}
