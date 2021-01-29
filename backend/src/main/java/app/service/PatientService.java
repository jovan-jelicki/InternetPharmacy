package app.service;

import app.dto.UserPasswordDTO;
import app.model.user.Patient;

public interface PatientService extends CRUDService<Patient> {
    void changePassword(UserPasswordDTO passwordKit);
}
