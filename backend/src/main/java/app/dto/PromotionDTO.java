package app.dto;

import app.model.medication.Medication;
import app.model.pharmacy.Promotion;
import app.model.time.Period;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PromotionDTO {
    private Long id;
    private Period period;
    private String content;
    private List<Medication> medicationsOnPromotion;
    private String pharmacyName;
    private Long pharmacyId;

    public PromotionDTO() {
    }

    public PromotionDTO(Promotion promotion) {
        this.id = promotion.getId();
        this.period = promotion.getPeriod();
        this.content = promotion.getContent();
        this.medicationsOnPromotion = promotion.getMedicationsOnPromotion();
        this.pharmacyId = promotion.getPharmacy().getId();
        this.pharmacyName = promotion.getPharmacy().getName();
    }
}
