package app.service.impl;

import app.dto.UserPasswordDTO;
import app.model.user.PharmacyAdmin;
import app.repository.PharmacyAdminRepository;
import app.service.PharmacyAdminService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PharmacyAdminServiceImpl implements PharmacyAdminService {

    private final PharmacyAdminRepository pharmacyAdminRepository;

    public PharmacyAdminServiceImpl(PharmacyAdminRepository pharmacyAdminRepository) {
        this.pharmacyAdminRepository = pharmacyAdminRepository;
    }

    @Override
    public PharmacyAdmin save(PharmacyAdmin entity) {
        return pharmacyAdminRepository.save(entity);
    }

    @Override
    public Collection<PharmacyAdmin> read() {
        return pharmacyAdminRepository.findAll();
    }

    @Override
    public Optional<PharmacyAdmin> read(Long id) {
        return pharmacyAdminRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        pharmacyAdminRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return pharmacyAdminRepository.existsById(id);
    }

    @Override
    public void changePassword(UserPasswordDTO passwordKit) {
        Optional<PharmacyAdmin> pharmacyAdmin = this.read(passwordKit.getUserId());
        if(pharmacyAdmin.isEmpty())
            throw new NullPointerException("User not found");
        PharmacyAdmin user = pharmacyAdmin.get();
        validatePassword(passwordKit, user);
        user.getCredentials().setPassword(passwordKit.getNewPassword());
        this.save(user);
    }

    private void validatePassword(UserPasswordDTO passwordKit, PharmacyAdmin user) {
        String password = user.getCredentials().getPassword();
        if(!password.equals(passwordKit.getOldPassword()))
            throw new IllegalArgumentException("Wrong password");
        else if(!passwordKit.getNewPassword().equals(passwordKit.getRepeatedPassword()))
            throw new IllegalArgumentException("Entered passwords doesn't match");
    }
}