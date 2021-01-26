package app.service;

import app.model.medication.Medication;

import java.util.Collection;

public interface MedicationService extends CRUDService<Medication> {
    Collection<Medication> fetchMedicationAlternatives(Long id);
}
