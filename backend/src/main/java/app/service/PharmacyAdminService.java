package app.service;

import app.dto.UserPasswordDTO;
import app.model.Ingredient;
import app.model.PharmacyAdmin;

public interface PharmacyAdminService extends CRUDService<PharmacyAdmin>{
    void changePassword(UserPasswordDTO passwordKit);

}
