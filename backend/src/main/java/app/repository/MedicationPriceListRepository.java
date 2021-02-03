package app.repository;

import app.model.medication.MedicationPriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MedicationPriceListRepository extends JpaRepository<MedicationPriceList, Long> {

    @Query("select m from MedicationPriceList m where m.pharmacy.id = ?1 and m.medication.id = ?2 and m.period.periodStart <= ?3 and m.period.periodEnd >= ?3")
    MedicationPriceList GetMedicationPriceInPharmacyByDate(Long pharmacyId, Long medicationId, LocalDateTime date);

    @Query("select m from MedicationPriceList m where m.pharmacy.id = ?1 and m.period.periodStart <= ?2 and m.period.periodEnd >= ?2")
    Collection<MedicationPriceList> getCurrentPriceListsByPharmacy(Long pharmacyId, LocalDateTime date);

    @Query("select m from MedicationPriceList m where m.pharmacy.id = ?1 and m.medication.id = ?2")
    Collection<MedicationPriceList> getMedicationPriceListHistoryByPharmacy(Long pharmacyId, Long medicationId);

    @Query("select m.cost from MedicationPriceList m where m.pharmacy.id = ?1 and m.medication.id = ?2 and m.period.periodStart <= ?3 and m.period.periodEnd >= ?3" )
    Double getMedicationPrice(Long pharmacyId, Long medicationId, LocalDateTime date);
}
