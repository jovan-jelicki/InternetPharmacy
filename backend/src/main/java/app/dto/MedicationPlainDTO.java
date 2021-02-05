package app.dto;

import app.model.medication.Medication;

import java.util.Objects;

public class MedicationPlainDTO {
    private Long id;
    private String name;
    private int loyaltyPoints;
    private String manufacturer;
    private String note;

    public MedicationPlainDTO(Medication medication) {
        this.id = medication.getId();
        this.name = medication.getName();
        this.loyaltyPoints = medication.getLoyaltyPoints();
        this.manufacturer = medication.getManufacturer();
        this.note = medication.getNote();
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

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicationPlainDTO that = (MedicationPlainDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
