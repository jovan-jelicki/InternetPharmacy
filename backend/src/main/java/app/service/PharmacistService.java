package app.service;

import app.dto.UserPasswordDTO;
import app.model.Pharmacist;


public interface PharmacistService extends CRUDService<Pharmacist> {
    void changePassword(UserPasswordDTO passwordKit);
}
