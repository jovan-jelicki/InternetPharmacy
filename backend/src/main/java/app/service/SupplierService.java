package app.service;

import app.dto.UserPasswordDTO;
import app.model.user.Patient;
import app.model.user.Supplier;

public interface SupplierService extends CRUDService<Supplier> {
    Supplier findByEmailAndPassword(String email, String password);
}
