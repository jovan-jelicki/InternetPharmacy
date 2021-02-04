package app.service;


import app.dto.MedicationOfferDTO;
import app.model.medication.MedicationOffer;

import java.util.Collection;

public interface MedicationOfferService extends CRUDService<MedicationOffer>{
    Boolean createNewMedicationOffer(MedicationOfferDTO medicationOffer);

    Collection<MedicationOfferDTO> getOffersByOrderId(Long orderId);
    //Collection<MedicationOffer> getMedicationOfferBySupplier(Long supplierId);
}
