package app.controller;

import app.dto.UserPasswordDTO;
import app.model.medication.Ingredient;
import app.model.user.Patient;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface PatientController extends CRUDController<Patient>{
    ResponseEntity<Collection<Ingredient>> getPatientAllergies(Long id);
    ResponseEntity<Void> changePassword(UserPasswordDTO passwordKit);
}
