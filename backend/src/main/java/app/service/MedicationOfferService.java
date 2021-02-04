package app.service;


import app.dto.MedicationOfferDTO;
import app.dto.MedicationOrderDTO;
import app.model.medication.MedicationOffer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface MedicationOfferService extends CRUDService<MedicationOffer>{
    Boolean createNewMedicationOffer(@RequestBody MedicationOfferDTO medicationOffer);
}
