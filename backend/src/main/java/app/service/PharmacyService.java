package app.service;

import app.dto.GetMedicationReservationDTO;
import app.dto.PharmacySearchDTO;
import app.model.medication.MedicationReservation;
import app.model.pharmacy.Pharmacy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

public interface PharmacyService extends CRUDService<Pharmacy> {
    Collection<Pharmacy> searchByNameAndAddress(PharmacySearchDTO pharmacySearchDTO);

    MedicationReservation getMedicationReservationFromPharmacy(GetMedicationReservationDTO getMedicationReservationDTO);
}
