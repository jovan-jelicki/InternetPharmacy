package app.service;

import app.dto.UserPasswordDTO;
import app.model.user.Patient;
import app.model.user.Pharmacist;
import app.model.user.PharmacyAdmin;
import app.model.user.SystemAdmin;

public interface PharmacyAdminService extends CRUDService<PharmacyAdmin>{
    void changePassword(UserPasswordDTO passwordKit);
    PharmacyAdmin findByEmailAndPassword(String email, String password);
    PharmacyAdmin findByEmail(String email);

}
