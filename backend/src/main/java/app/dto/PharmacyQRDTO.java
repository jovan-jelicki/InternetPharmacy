package app.dto;

import app.model.user.Address;

public class PharmacyQRDTO {
    private String name;
    private Address address;
    private Long medicationId;
    private int medicationQuantity;
    private Double medicationPrice;
    private Double pharmacyGrade;

    public PharmacyQRDTO(){}

    public PharmacyQRDTO(String name, Address address, Long medicationId, int medicationQuantity, Double medicationPrice, Double pharmacyGrade) {
        this.name = name;
        this.address = address;
        this.medicationId = medicationId;
        this.medicationQuantity = medicationQuantity;
        this.medicationPrice = medicationPrice;
        this.pharmacyGrade = pharmacyGrade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public int getMedicationQuantity() {
        return medicationQuantity;
    }

    public void setMedicationQuantity(int medicationQuantity) {
        this.medicationQuantity = medicationQuantity;
    }

    public Double getMedicationPrice() {
        return medicationPrice;
    }

    public void setMedicationPrice(Double medicationPrice) {
        this.medicationPrice = medicationPrice;
    }

    public Double getPharmacyGrade() {
        return pharmacyGrade;
    }

    public void setPharmacyGrade(Double pharmacyGrade) {
        this.pharmacyGrade = pharmacyGrade;
    }
}
