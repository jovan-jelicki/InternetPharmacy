package app.repository;

import app.model.medication.MedicationPriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicationPriceListRepository extends JpaRepository<MedicationPriceList, Long> {

    @Query("select m from MedicationPriceList m where m.pharmacy.id = ?1 and m.medication.id = ?2 and m.period.periodStart <= ?3 and m.period.periodEnd >= ?3")
    MedicationPriceList GetMedicationPriceInPharmacyByDate(Long pharmacyId, Long medicationId, LocalDateTime date);
}
