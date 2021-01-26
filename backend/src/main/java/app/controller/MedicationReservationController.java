package app.controller;

import app.dto.GetMedicationReservationDTO;
import app.model.medication.MedicationReservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface MedicationReservationController extends CRUDController<MedicationReservation> {
    ResponseEntity<Void> giveMedicine(@RequestBody MedicationReservation entity);

    ResponseEntity<MedicationReservation> getMedicationReservationFromPharmacy(GetMedicationReservationDTO getMedicationReservationDTO);
}