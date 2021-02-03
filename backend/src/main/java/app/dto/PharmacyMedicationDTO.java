package app.dto;

import app.model.user.Address;

public class PharmacyMedicationDTO {
    private Long id;
    private String name;
    private Address address;
    private Long medicationId;
    private Double medicationPrice;

    public PharmacyMedicationDTO(){}

    public PharmacyMedicationDTO(Long id, String name, Address address, Long medicationId, Double medicationPrice) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.medicationId = medicationId;
        this.medicationPrice=medicationPrice;
    }

    public Double getMedicationPrice() {
        return medicationPrice;
    }

    public void setMedicationPrice(Double medicationPrice) {
        this.medicationPrice = medicationPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
