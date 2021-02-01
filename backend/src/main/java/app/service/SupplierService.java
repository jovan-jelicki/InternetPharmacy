package app.service;

import app.model.user.Supplier;
import app.model.user.SystemAdmin;

public interface SupplierService extends CRUDService<Supplier> {
    Supplier findByEmailAndPassword(String email, String password);
    Supplier findByEmail(String email);

}
