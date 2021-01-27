package app.service;

import app.dto.GetMedicationReservationDTO;
import app.model.medication.MedicationReservation;

public interface MedicationReservationService extends CRUDService<MedicationReservation> {
    MedicationReservation getMedicationReservationFromPharmacy(GetMedicationReservationDTO getMedicationReservationDTO);

}
