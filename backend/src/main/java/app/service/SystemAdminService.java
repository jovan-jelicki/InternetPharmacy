package app.service;

import app.model.user.Patient;
import app.model.user.SystemAdmin;

public interface SystemAdminService extends CRUDService<SystemAdmin> {
    SystemAdmin findByEmailAndPassword(String email, String password);

}
