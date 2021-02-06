package app.dto;

import app.model.user.Address;

public class PharmacyAdminPharmacyDTO {
    private String name;
    private String description;
    private Address address;
    private Long pharmacyAdminId;

    public PharmacyAdminPharmacyDTO() {
    }

    public PharmacyAdminPharmacyDTO(String name, String description, Address address, Long pharmacyAdminId) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.pharmacyAdminId = pharmacyAdminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getPharmacyAdminId() {
        return pharmacyAdminId;
    }

    public void setPharmacyAdminId(Long pharmacyAdminId) {
        this.pharmacyAdminId = pharmacyAdminId;
    }
}
