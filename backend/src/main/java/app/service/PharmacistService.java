package app.service;

import app.dto.PharmacyNameIdDTO;
import app.dto.UserPasswordDTO;
import app.model.user.Pharmacist;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;


public interface PharmacistService extends CRUDService<Pharmacist> {
    void changePassword(UserPasswordDTO passwordKit);

    Collection<Pharmacist> read();
    PharmacyNameIdDTO getPharmacyOfPharmacist(Long id);
    Collection<Pharmacist> getPharmacistsByPharmacyId(Long id);
}
