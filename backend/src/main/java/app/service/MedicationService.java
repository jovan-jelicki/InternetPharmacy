package app.service;

import app.model.medication.Ingredient;
import app.model.medication.Medication;
import app.model.medication.SideEffect;

import java.util.Collection;

public interface MedicationService extends CRUDService<Medication> {
    Collection<Ingredient> fetchMedicationIngredients(Long id);
    Collection<SideEffect> fetchMedicationSideEffects(Long id);
    Collection<Medication> fetchMedicationAlternatives(Long id);
}
