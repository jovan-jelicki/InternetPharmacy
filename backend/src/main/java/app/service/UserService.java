package app.service;

import app.dto.UserPasswordDTO;
import app.model.user.User;

public interface UserService<T extends User> extends CRUDService<T>{
    void changePassword(UserPasswordDTO passwordKit);
}
