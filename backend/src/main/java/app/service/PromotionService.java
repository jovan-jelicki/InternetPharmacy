package app.service;

import app.model.medication.Medication;
import app.model.pharmacy.Promotion;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface PromotionService extends CRUDService<Promotion> {
    Collection<Promotion> getCurrentPromotionsByPharmacyAndDate(Long pharmacyId, LocalDateTime date);

    Boolean checkPatientSubscribedToPromotion(Long pharmacyId, Long patientId);

    Boolean subscribeToPromotion(Long patientId, Long promotionId);
    Boolean unsubscribe( Long pharmacyId,  Long patientId);
}
