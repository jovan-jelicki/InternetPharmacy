package app.controller;

import app.dto.UserPasswordDTO;
import app.model.Contact;
import app.model.Ingredient;
import app.model.Patient;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface PatientController extends CRUDController<Patient>{
    ResponseEntity<Collection<Ingredient>> getPatientAllergies(Long id);
    ResponseEntity<Void> changePassword(UserPasswordDTO passwordKit);
}
