package app.service;

import app.dto.PharmacyPlainDTO;
import app.dto.UserPasswordDTO;
import app.model.medication.Ingredient;
import app.model.medication.Medication;
import app.model.user.Patient;

import java.util.Collection;

public interface PatientService extends CRUDService<Patient> {
    void changePassword(UserPasswordDTO passwordKit);

    Collection<Ingredient> getPatientAllergieIngridients(Long id);
    Boolean isPatientAllergic(Collection<Medication> medication, Long id);

     Patient findByEmailAndPassword(String email, String password);
    Patient findByEmail(String email);

    Collection<PharmacyPlainDTO> getPromotionPharmacies(Long patientId);

    Boolean setPatientCategory(Long patientId);
}

