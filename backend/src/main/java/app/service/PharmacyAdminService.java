package app.service;


import app.dto.PharmacyAdminRegistrationDTO;
import app.dto.PharmacyDTO;
import app.dto.UserPasswordDTO;
import app.model.user.PharmacyAdmin;
import org.springframework.web.bind.annotation.RequestBody;

public interface PharmacyAdminService extends CRUDService<PharmacyAdmin>{
    void changePassword(UserPasswordDTO passwordKit);
    PharmacyAdmin findByEmailAndPassword(String email, String password);
    PharmacyAdmin findByEmail(String email);
    PharmacyAdmin getPharmacyAdminByPharmacy(Long pharmacyId);
    Boolean saveAdmin(PharmacyAdminRegistrationDTO pharmacyAdmin);
    PharmacyDTO getPharmacyByPharmacyAdmin(Long pharmacyAdminId);
    }
