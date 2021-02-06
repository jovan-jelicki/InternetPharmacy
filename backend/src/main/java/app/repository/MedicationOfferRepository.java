package app.repository;

import app.model.medication.MedicationOffer;
import app.model.medication.MedicationOfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MedicationOfferRepository extends JpaRepository<MedicationOffer, Long> {

    @Query("select o from MedicationOffer o where o.medicationOrder.id = ?1")
    Collection<MedicationOffer> getMedicationOffersByMedicationOrder(Long medicationOrderId);

    @Query("select o from MedicationOffer o where o.medicationOrder.pharmacyAdmin.pharmacy.id = ?1 and o.status = 1 and o.shippingDate >= ?2 and o.shippingDate <= ?3")
    Collection<MedicationOffer> getApprovedMedicationOffersByPharmacyAndPeriod(Long pharmacyId, LocalDateTime periodStart, LocalDateTime periodEnd);
}
