package app.dto;

import app.model.medication.MedicationOfferStatus;
import app.model.medication.MedicationOrderStatus;
import app.model.medication.MedicationQuantity;

import java.time.LocalDateTime;
import java.util.List;

public class MedicationOfferAndOrderDTO {
    private double cost;
    private LocalDateTime shippingDate;
    private MedicationOfferStatus offerStatus;
    private String pharmacyName;
    private LocalDateTime deadline;
    private List<MedicationQuantity> medicationQuantity;
    private MedicationOrderStatus orderStatus;

    public MedicationOfferAndOrderDTO() {
    }

    public MedicationOfferAndOrderDTO(double cost, LocalDateTime shippingDate, MedicationOfferStatus offerStatus, String pharmacyName, LocalDateTime deadline, List<MedicationQuantity> medicationQuantity, MedicationOrderStatus orderStatus) {
        this.cost = cost;
        this.shippingDate = shippingDate;
        this.offerStatus = offerStatus;
        this.pharmacyName = pharmacyName;
        this.deadline = deadline;
        this.medicationQuantity = medicationQuantity;
        this.orderStatus = orderStatus;
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

    public MedicationOfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(MedicationOfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getPharmacyAdminId() {
        return pharmacyName;
    }

    public void setPharmacyAdminId(String pharmacyName) {
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

    public MedicationOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(MedicationOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
