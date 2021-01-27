package app.service;

import app.dto.UserPasswordDTO;
import app.model.user.Dermatologist;

import java.util.Collection;

public interface DermatologistService extends CRUDService<Dermatologist> {
    void changePassword(UserPasswordDTO passwordKit);

    Collection<Dermatologist> getAllDermatologistNotWorkingInPharmacy(Long id);

    Collection<Dermatologist> getAllDermatologistWorkingInPharmacy(Long id);

}
