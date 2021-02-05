package app.dto;

import app.model.pharmacy.Pharmacy;
import app.model.user.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Objects;

public class PharmacyPlainDTO {
    private Long id;
    private String name;
    private Address address;
    private String description;
    private int pharmacistCost;
    private int dermatologistCost;
    private double grade;
    
    public PharmacyPlainDTO() {}

    public PharmacyPlainDTO(Pharmacy pharmacy) {
        this.id = pharmacy.getId();
        this.name = pharmacy.getName();
        this.address = pharmacy.getAddress();
        this.description = pharmacy.getDescription();
        this.pharmacistCost = pharmacy.getPharmacistCost();
        this.dermatologistCost = pharmacy.getDermatologistCost();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPharmacistCost() {
        return pharmacistCost;
    }

    public void setPharmacistCost(int pharmacistCost) {
        this.pharmacistCost = pharmacistCost;
    }

    public int getDermatologistCost() {
        return dermatologistCost;
    }

    public void setDermatologistCost(int dermatologistCost) {
        this.dermatologistCost = dermatologistCost;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PharmacyPlainDTO that = (PharmacyPlainDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
