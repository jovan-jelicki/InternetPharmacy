package app.service;

import app.dto.MedicationOfferAndOrderDTO;
import app.dto.MedicationSupplierDTO;
import app.dto.UserPasswordDTO;
import app.model.medication.Medication;
import app.model.medication.MedicationOffer;
import app.model.medication.MedicationQuantity;
import app.model.user.Supplier;

import java.util.Collection;

public interface SupplierService extends CRUDService<Supplier> {
    Supplier findByEmailAndPassword(String email, String password);
    Supplier findByEmail(String email);
    Collection<MedicationOfferAndOrderDTO> getMedicationOffersBySupplier(Long supplierId);
    Collection<MedicationQuantity> getSuppliersMedicationList(Long supplierId);
    Collection<Medication> getNonMedicationsBySupplier( Long supplierId);
    Boolean addNewMedication( MedicationSupplierDTO medicationSupplierDTO);
    Boolean editSuppliersMedicationQuantity(MedicationSupplierDTO medicationSupplierDTO);
    Boolean deleteMedicationQuantity( MedicationSupplierDTO   medicationSupplierDTO);
    Supplier getSupplierByMedicationOffer(MedicationOffer medicationOffer);
    void changePassword(UserPasswordDTO passwordKit);

}
