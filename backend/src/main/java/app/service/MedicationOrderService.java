package app.service;

import app.dto.MedicationOrderDTO;
import app.model.medication.MedicationOrder;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

public interface MedicationOrderService extends CRUDService<MedicationOrder> {

    Boolean createNewMedicationOrder(MedicationOrderDTO medicationOrderDTO);

    Collection<MedicationOrderDTO> getAllMedicationOrdersByPharmacy(Long pharmacyId);

    Collection<MedicationOrderDTO> getMedicationOrderByPharmacyAdmin(Long pharmacyAdminId);
}
