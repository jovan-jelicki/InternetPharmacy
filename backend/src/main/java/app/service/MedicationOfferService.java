package app.service;


import app.dto.MedicationOfferDTO;
import app.dto.MedicationOrderDTO;
import app.model.medication.MedicationOffer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

public interface MedicationOfferService extends CRUDService<MedicationOffer>{
    Boolean createNewMedicationOffer(MedicationOfferDTO medicationOffer);
    //Collection<MedicationOffer> getMedicationOfferBySupplier(Long supplierId);
}
