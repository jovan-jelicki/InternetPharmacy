package app.dto;

import app.model.medication.MedicationPriceList;
import app.model.time.Period;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicationPriceListDTO {
    private Long id;
    private Long medicationId;
    private Long pharmacyId;
    private double cost;
    private Period period;
    private String medicationName;

    public MedicationPriceListDTO() {
    }

    public MedicationPriceListDTO(MedicationPriceList medicationPriceList) {
        this.id = medicationPriceList.getId();
        this.medicationId = medicationPriceList.getMedication().getId();
        this.pharmacyId = medicationPriceList.getPharmacy().getId();
        this.cost = medicationPriceList.getCost();
        this.period = medicationPriceList.getPeriod();
        this.medicationName = medicationPriceList.getMedication().getName();
    }
}
