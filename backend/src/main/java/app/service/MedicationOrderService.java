package app.service;

import app.dto.MedicationOrderDTO;
import app.dto.MedicationOrderSupplierDTO;
import app.model.medication.MedicationOrder;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface MedicationOrderService extends CRUDService<MedicationOrder> {

    Boolean createNewMedicationOrder(MedicationOrderDTO medicationOrderDTO);

    Collection<MedicationOrderDTO> getAllMedicationOrdersByPharmacy(Long pharmacyId);

    Collection<MedicationOrderDTO> getMedicationOrderByPharmacyAdmin(Long pharmacyAdminId);

    Boolean deleteMedicationOrder(Long orderId);

    void setMedicationOfferService(MedicationOfferService medicationOfferService);

    Boolean editMedicationOrder(MedicationOrderDTO medicationOrderDTO);

    Boolean checkIfOrderIsEditable(Long orderId);
    Collection<MedicationOrderSupplierDTO> getAllActive();
}
