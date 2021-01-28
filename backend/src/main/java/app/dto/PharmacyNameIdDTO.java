package app.dto;

import app.model.pharmacy.Pharmacy;

public class PharmacyNameIdDTO {
    private Long id;
    private String name;

    public PharmacyNameIdDTO(Pharmacy pharmacy) {
        this.id = pharmacy.getId();
        this.name = pharmacy.getName();
    }

    public PharmacyNameIdDTO() {
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
}
