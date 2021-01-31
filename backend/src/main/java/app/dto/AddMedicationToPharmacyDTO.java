package app.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddMedicationToPharmacyDTO {
    public Long pharmacyId;
    public Long medicationId;
    public int quantity;
    public LocalDateTime priceDateStart;
    public LocalDateTime priceDateEnd;
    public int cost;

    public AddMedicationToPharmacyDTO() {
    }


}
