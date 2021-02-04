package app.service;

import app.model.medication.MedicationOffer;
import app.model.user.Supplier;
import app.model.user.SystemAdmin;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

public interface SupplierService extends CRUDService<Supplier> {
    Supplier findByEmailAndPassword(String email, String password);
    Supplier findByEmail(String email);
    Collection<MedicationOffer> getMedicationOffersBySupplier(Long supplierId);
}
