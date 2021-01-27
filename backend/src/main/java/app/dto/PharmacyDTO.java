package app.dto;

import app.model.medication.MedicationQuantity;
import app.model.pharmacy.Pharmacy;
import app.model.user.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
public class PharmacyDTO {
    @Id
    private Long id;
    private String name;
    private Address address;
    private String description;

    private List<MedicationQuantity> medicationQuantity;

    public PharmacyDTO() {}

    public PharmacyDTO(Pharmacy pharmacy) {
        this.id = pharmacy.getId();
        this.name = pharmacy.getName();
        this.address = pharmacy.getAddress();
        this.description = pharmacy.getDescription();
        this.medicationQuantity = pharmacy.getMedicationQuantity();
    }
}
