package app.service;

import app.dto.UserPasswordDTO;
import app.model.user.Dermatologist;

public interface DermatologistService extends CRUDService<Dermatologist> {
    void changePassword(UserPasswordDTO passwordKit);
}
