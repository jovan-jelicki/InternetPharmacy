package app.service;

import app.dto.UserPasswordDTO;
import app.model.Dermatologist;

public interface DermatologistService extends CRUDService<Dermatologist> {
    void changePassword(UserPasswordDTO passwordKit);
}
