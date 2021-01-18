package app.service.impl;

import app.dto.UserPasswordDTO;
import app.model.user.Dermatologist;
import app.repository.DermatologistRepository;
import app.service.DermatologistService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class DermatologistServiceImpl implements DermatologistService {
    private DermatologistRepository dermatologistRepository;

    public DermatologistServiceImpl(DermatologistRepository dermatologistRepository) {
        this.dermatologistRepository = dermatologistRepository;
    }

    @Override
    public void changePassword(UserPasswordDTO passwordKit) {
        Optional<Dermatologist> _user = dermatologistRepository.findById(passwordKit.getUserId());
        if(_user.isEmpty())
            throw new NullPointerException("User not found");
        Dermatologist user = _user.get();
        validatePassword(passwordKit, user);
        user.getCredentials().setPassword(passwordKit.getNewPassword());
        save(user);
    }

    private void validatePassword(UserPasswordDTO passwordKit, Dermatologist user) {
        String password = user.getCredentials().getPassword();
        if(!password.equals(passwordKit.getOldPassword()))
            throw new IllegalArgumentException("Wrong password");
        else if(!passwordKit.getNewPassword().equals(passwordKit.getRepeatedPassword()))
            throw new IllegalArgumentException("Entered passwords doesn't match");
    }
    @Override
    public Dermatologist save(Dermatologist entity) {
        return dermatologistRepository.save(entity);
    }

    @Override
    public Collection<Dermatologist> read() {
        return dermatologistRepository.findAll();
    }

    @Override
    public Optional<Dermatologist> read(Long id) {
        return dermatologistRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        dermatologistRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return dermatologistRepository.existsById(id);
    }
}
