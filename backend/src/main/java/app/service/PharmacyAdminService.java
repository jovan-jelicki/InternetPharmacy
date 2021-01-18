package app.service;

import app.dto.UserPasswordDTO;
import app.model.user.PharmacyAdmin;

public interface PharmacyAdminService extends CRUDService<PharmacyAdmin>{
    void changePassword(UserPasswordDTO passwordKit);

}
