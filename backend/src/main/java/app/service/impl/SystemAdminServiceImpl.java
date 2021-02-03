package app.service.impl;

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

}
