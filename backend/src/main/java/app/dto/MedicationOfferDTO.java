package app.dto;

import app.model.medication.MedicationOffer;
import app.model.medication.MedicationOfferStatus;
import app.model.user.Supplier;

import java.time.LocalDateTime;

public class MedicationOfferDTO {
    private Long id;
    private double cost;
    private LocalDateTime shippingDate;
    private MedicationOfferStatus status;
    private Long medicationOrderId;
    private Long supplierId;
    private String supplierFirstName;
    private String supplierLastName;
    private Long medicationOfferVersion;
    private Long medicationOrderVersion;

    public MedicationOfferDTO(){}

    public MedicationOfferDTO(Long id, double cost, LocalDateTime shippingDate, MedicationOfferStatus status, Long medicationOrderId, Long supplierId, String supplierFirstName, String supplierLastName) {
        this.id = id;
        this.cost = cost;
        this.shippingDate = shippingDate;
        this.status = status;
        this.medicationOrderId = medicationOrderId;
        this.supplierId = supplierId;
        this.supplierFirstName = supplierFirstName;
        this.supplierLastName = supplierLastName;
    }

    public MedicationOfferDTO(Supplier supplier, MedicationOffer medicationOffer) {
        this.id = medicationOffer.getId();
        this.cost = medicationOffer.getCost();
        this.shippingDate = medicationOffer.getShippingDate();
        this.status = medicationOffer.getStatus();
        this.medicationOrderId = medicationOffer.getMedicationOrder().getId();
        this.supplierId = supplier.getId();
        this.supplierFirstName = supplier.getFirstName();
        this.supplierLastName = supplier.getLastName();
        this.medicationOfferVersion = medicationOffer.getVersion();
        this.medicationOrderVersion = medicationOffer.getMedicationOrder().getVersion();
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

    public String getSupplierFirstName() {
        return supplierFirstName;
    }

    public void setSupplierFirstName(String supplierFirstName) {
        this.supplierFirstName = supplierFirstName;
    }

    public String getSupplierLastName() {
        return supplierLastName;
    }

    public void setSupplierLastName(String supplierLastName) {
        this.supplierLastName = supplierLastName;
    }

    public Long getMedicationOfferVersion() {
        return medicationOfferVersion;
    }

    public void setMedicationOfferVersion(Long medicationOfferVersion) {
        this.medicationOfferVersion = medicationOfferVersion;
    }

    public Long getMedicationOrderVersion() {
        return medicationOrderVersion;
    }

    public void setMedicationOrderVersion(Long medicationOrderVersion) {
        this.medicationOrderVersion = medicationOrderVersion;
    }
}
