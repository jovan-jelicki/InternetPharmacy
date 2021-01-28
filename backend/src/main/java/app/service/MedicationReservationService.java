package app.service;

import app.dto.GetMedicationReservationDTO;
import app.dto.MakeMedicationReservationDTO;
import app.model.medication.MedicationReservation;

public interface MedicationReservationService extends CRUDService<MedicationReservation> {
    MedicationReservation getMedicationReservationFromPharmacy(GetMedicationReservationDTO getMedicationReservationDTO);
    MedicationReservation reserve(MakeMedicationReservationDTO entity);
}
