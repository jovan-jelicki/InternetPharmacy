package app.controller;

import app.dto.GetMedicationReservationDTO;
import app.dto.MedicationReservationSimpleInfoDTO;
import app.model.medication.MedicationReservation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface MedicationReservationController extends CRUDController<MedicationReservation> {
    ResponseEntity<Void> giveMedicine(@PathVariable Long id);

    ResponseEntity<MedicationReservationSimpleInfoDTO> getMedicationReservationFromPharmacy(GetMedicationReservationDTO getMedicationReservationDTO);
}