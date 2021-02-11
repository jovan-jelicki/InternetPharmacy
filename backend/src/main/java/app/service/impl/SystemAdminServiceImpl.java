package app.service.impl;

import app.dto.UserPasswordDTO;
import app.model.user.SystemAdmin;
import app.repository.SystemAdminRepository;
import app.service.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class SystemAdminServiceImpl implements SystemAdminService {
    private SystemAdminRepository systemAdminRepository;

    @Autowired
    public SystemAdminServiceImpl(SystemAdminRepository systemAdminRepository) {
        this.systemAdminRepository = systemAdminRepository;
    }

    @Override
    public SystemAdmin save(SystemAdmin entity) {return systemAdminRepository.save(entity);}

    @Override
    public Collection<SystemAdmin> read() {return systemAdminRepository.findAll();}

    @Override
    public Optional<SystemAdmin> read(Long id)  {return systemAdminRepository.findById(id); }

    @Override
    public void delete(Long id)  {
        systemAdminRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id)  {
        return systemAdminRepository.existsById(id);
    }

    public SystemAdmin findByEmailAndPassword(String email, String password) { return systemAdminRepository.findByEmailAndPassword(email, password);}

    @Override
    public SystemAdmin findByEmail(String email) {
        return systemAdminRepository.findByEmail(email);
    }

    private void validatePassword(UserPasswordDTO passwordKit, SystemAdmin user) {
        String password = user.getCredentials().getPassword();
        if(!password.equals(passwordKit.getOldPassword()))
            throw new IllegalArgumentException("Wrong password");
        else if(!passwordKit.getNewPassword().equals(passwordKit.getRepeatedPassword()))
            throw new IllegalArgumentException("Entered passwords doesn't match");
    }

    @Override
    public void changePassword(UserPasswordDTO passwordKit) {
        Optional<SystemAdmin> _user = systemAdminRepository.findById(passwordKit.getUserId());
        SystemAdmin user = _user.get();
        validatePassword(passwordKit, user);
        user.getCredentials().setPassword(passwordKit.getNewPassword());
        user.setApprovedAccount(true);
        save(user);
    }

}
