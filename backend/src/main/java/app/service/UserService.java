package app.service;

import app.dto.UserPasswordDTO;
import app.model.User;

public interface UserService<T extends User> extends CRUDService<T>{
    boolean existsById(Long id);
    void changePassword(UserPasswordDTO passwordKit);
}
