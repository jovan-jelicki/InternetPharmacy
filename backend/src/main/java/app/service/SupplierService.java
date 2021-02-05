package app.service;

import app.dto.MedicationOfferAndOrderDTO;
import app.dto.MedicationQuantityDTO;
import app.model.user.Supplier;

import java.util.Collection;

public interface SupplierService extends CRUDService<Supplier> {
    Supplier findByEmailAndPassword(String email, String password);
    Supplier findByEmail(String email);
    Collection<MedicationOfferAndOrderDTO> getMedicationOffersBySupplier(Long supplierId);
    Collection<MedicationQuantityDTO> getSuppliersMedicationList(Long supplierId);
}
