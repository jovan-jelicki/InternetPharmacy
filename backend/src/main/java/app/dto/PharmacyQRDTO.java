package app.dto;

import app.model.medication.MedicationQuantity;
import app.model.user.Address;

public class PharmacyQRDTO {
    private String name;
    private Address address;
    private Long pharmacyId;
    private Long ePrescriptionId;
    private String medicationName;
    private MedicationQuantity medicationQuantity;
    private Double medicationPrice;
    private Double pharmacyGrade;

    public PharmacyQRDTO(){}

    public PharmacyQRDTO(String name, Address address, Long medicationId, Long pharmacyId, Long ePrescriptionId, String medicationName, MedicationQuantity medicationQuantity, Double medicationPrice, Double pharmacyGrade) {
        this.name = name;
        this.address = address;
        this.pharmacyId = pharmacyId;
        this.ePrescriptionId = ePrescriptionId;
        this.medicationName = medicationName;
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

    public MedicationQuantity getMedicationQuantity() {
        return medicationQuantity;
    }

    public void setMedicationQuantity(MedicationQuantity medicationQuantity) {this.medicationQuantity = medicationQuantity; }

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

    public String getMedicationName() { return medicationName;}

    public void setMedicationName(String medicationName) {this.medicationName = medicationName;}

    public Long getPharmacyId() { return pharmacyId;}

    public void setPharmacyId(Long pharmacyId) { this.pharmacyId = pharmacyId;}

    public Long getePrescriptionId() {return ePrescriptionId; }

    public void setePrescriptionId(Long ePrescriptionId) { this.ePrescriptionId = ePrescriptionId;}
}
