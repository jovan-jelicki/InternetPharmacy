package app.service;

import app.dto.MedicationGradeDTO;
import app.dto.MedicationSearchDTO;
import app.model.medication.Medication;

import java.util.Collection;

public interface MedicationService extends CRUDService<Medication> {
    Collection<Medication> fetchMedicationAlternatives(Long id);
    Collection<Medication> getAllMedicationsPatientIsNotAllergicTo(Long patientId);

    Collection<Medication> getMedicationsNotContainedInPharmacy(Long pharmacyId);

    Medication getMedicationByName(String name);
    Collection<Medication> getMedicationByNameIsContaining(MedicationSearchDTO name);
    Collection<MedicationGradeDTO> readMediactionAndGrades();

}
