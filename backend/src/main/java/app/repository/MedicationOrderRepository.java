package app.repository;

import app.dto.MedicationOrderDTO;
import app.model.medication.MedicationOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface MedicationOrderRepository extends JpaRepository<MedicationOrder, Long> {

    @Query("select m from MedicationOrder m where m.pharmacyAdmin.pharmacy.id = ?1 and m.isActive=true")
    Collection<MedicationOrder> getAllMedicationOrdersByPharmacy(Long pharmacyId);

    Collection<MedicationOrderDTO> getMedicationOrderByPharmacyAdmin(Long pharmacyAdminId);

}
