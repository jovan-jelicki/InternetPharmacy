package app.service;

import app.model.pharmacy.Pharmacy;
import app.model.pharmacy.Promotion;
import app.model.user.Patient;

import java.time.LocalDateTime;
import java.util.Collection;

public interface PromotionService extends CRUDService<Promotion> {
    Collection<Promotion> getCurrentPromotionsByPharmacyAndDate(Long pharmacyId, LocalDateTime date);
    Boolean checkPatientSubscribedToPromotion(Long pharmacyId, Long patientId, Long medicationId);
    Boolean subscribeToPromotion(Long patientId, Long promotionId);
    Boolean unsubscribe( Long pharmacyId,  Long patientId);
    Collection<Patient> getAllPatientsSubscribedToPharmacyPromotions(Pharmacy pharmacy);
}
