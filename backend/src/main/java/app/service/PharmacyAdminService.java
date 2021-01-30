package app.service;

import app.dto.UserPasswordDTO;
import app.model.user.Patient;
import app.model.user.PharmacyAdmin;

public interface PharmacyAdminService extends CRUDService<PharmacyAdmin>{
    void changePassword(UserPasswordDTO passwordKit);
    PharmacyAdmin findByEmailAndPassword(String email, String password);

}
