package app.service;

import app.dto.UserPasswordDTO;
import app.model.Address;
import app.model.User;

public interface UserService<T extends User> extends CRUDService<T>{
    void changePassword(UserPasswordDTO passwordKit);
}
