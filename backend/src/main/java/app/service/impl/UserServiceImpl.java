package app.service.impl;

import app.dto.UserPasswordDTO;
import app.model.User;
import app.repository.UserRepository;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl<T extends User> implements UserService<T> {
    
    private final UserRepository<T> userRepository;
    
    @Autowired
    public UserServiceImpl(UserRepository<T> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public T save(T entity) {
        return userRepository.save(entity);
    }

    @Override
    public Collection<T> read() {
        return userRepository.findAll();
    }

    @Override
    public Optional<T> read(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public void changePassword(UserPasswordDTO passwordKit) {
        Optional<T> _user = userRepository.findById(passwordKit.getUserId());
        if(_user.isEmpty())
            throw new NullPointerException("User not found");
        T user = _user.get();
        validatePassword(passwordKit, user);
        user.getCredentials().setPassword(passwordKit.getNewPassword());
        save(user);
    }

    private void validatePassword(UserPasswordDTO passwordKit, T user) {
        String password = user.getCredentials().getPassword();
        if(!password.equals(passwordKit.getOldPassword()))
            throw new IllegalArgumentException("Wrong password");
        else if(!passwordKit.getNewPassword().equals(passwordKit.getRepeatedPassword()))
            throw new IllegalArgumentException("Entered passwords doesn't match");
    }
}
