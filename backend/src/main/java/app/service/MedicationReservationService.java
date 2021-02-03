package app.service;

import app.dto.GetMedicationReservationDTO;
import app.dto.MakeMedicationReservationDTO;
import app.model.medication.MedicationReservation;
import app.model.user.Patient;

public interface MedicationReservationService extends CRUDService<MedicationReservation> {
    MedicationReservation getMedicationReservationFromPharmacy(GetMedicationReservationDTO getMedicationReservationDTO);
    MedicationReservation reserve(MakeMedicationReservationDTO entity);
    void sendEmailToPatient(Patient patient);

    }
