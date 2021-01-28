package app.service;

import app.dto.PharmacyNameIdDTO;
import app.dto.UserPasswordDTO;
import app.model.user.Dermatologist;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

public interface DermatologistService extends CRUDService<Dermatologist> {
    void changePassword(UserPasswordDTO passwordKit);

    Collection<Dermatologist> getAllDermatologistNotWorkingInPharmacy(Long id);
    Collection<PharmacyNameIdDTO> getPharmacyOfPharmacist(Long id);
    Collection<Dermatologist> getAllDermatologistWorkingInPharmacy(Long id);

}
