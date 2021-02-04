package app.service;


import app.dto.MedicationOfferDTO;
import app.model.medication.MedicationOffer;

public interface MedicationOfferService extends CRUDService<MedicationOffer>{
    Boolean createNewMedicationOffer(MedicationOfferDTO medicationOffer);
    //Collection<MedicationOffer> getMedicationOfferBySupplier(Long supplierId);
}
