package app.service;

import app.dto.UserPasswordDTO;
import app.model.user.SystemAdmin;

public interface SystemAdminService extends CRUDService<SystemAdmin> {
    SystemAdmin findByEmailAndPassword(String email, String password);
    SystemAdmin findByEmail(String email);
    void changePassword(UserPasswordDTO passwordKit);
}
