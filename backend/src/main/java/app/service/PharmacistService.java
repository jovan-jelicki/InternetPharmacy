package app.service;

import app.dto.PharmacyNameIdDTO;
import app.dto.UserPasswordDTO;
import app.model.user.Patient;
import app.model.user.Pharmacist;
import app.model.user.SystemAdmin;

import java.util.Collection;


public interface PharmacistService extends CRUDService<Pharmacist> {
    void changePassword(UserPasswordDTO passwordKit);

    Collection<Pharmacist> read();
    PharmacyNameIdDTO getPharmacyOfPharmacist(Long id);
    Collection<Pharmacist> getPharmacistsByPharmacyId(Long id);
    Pharmacist findByEmailAndPassword(String email, String password);
    Pharmacist findByEmail(String email);

}
