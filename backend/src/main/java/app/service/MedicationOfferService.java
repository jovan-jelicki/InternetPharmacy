package app.service;


import app.dto.MedicationOfferAndOrderDTO;
import app.dto.MedicationOfferDTO;
import app.model.medication.MedicationOffer;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MedicationOfferService extends CRUDService<MedicationOffer>{
    Boolean createNewMedicationOffer(MedicationOfferDTO medicationOffer);
    Boolean editMedicationOffer(MedicationOfferAndOrderDTO medicationOffer);
    Collection<MedicationOfferDTO> getOffersByOrderId(Long orderId);
    Boolean acceptOffer(MedicationOfferDTO medicationOfferDTO, Long pharmacyAdminId);
    //Collection<MedicationOffer> getMedicationOfferBySupplier(Long supplierId);
    Collection<MedicationOffer> getApprovedMedicationOffersByPharmacyAndPeriod(Long pharmacyId, LocalDateTime periodStart, LocalDateTime periodEnd);
}
