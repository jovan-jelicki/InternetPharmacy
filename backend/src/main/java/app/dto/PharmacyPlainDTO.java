package app.dto;

import app.model.pharmacy.Pharmacy;
import app.model.user.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class PharmacyPlainDTO {
    private Long id;
    private String name;
    private Address address;
    private String description;
    
    public PharmacyPlainDTO() {}

    public PharmacyPlainDTO(Pharmacy pharmacy) {
        this.id = pharmacy.getId();
        this.name = pharmacy.getName();
        this.address = pharmacy.getAddress();
        this.description = pharmacy.getDescription();
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
