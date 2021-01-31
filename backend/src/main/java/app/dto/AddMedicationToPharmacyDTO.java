package app.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddMedicationToPharmacyDTO {
    public Long pharmacyId;
    public Long medicationId;
    public int quantity;
    public LocalDate priceDateStart;
    public LocalDate priceDateEnd;

    public AddMedicationToPharmacyDTO() {
    }


}
