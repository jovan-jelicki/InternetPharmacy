package app.repository;

import app.model.medication.MedicationOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface MedicationOfferRepository extends JpaRepository<MedicationOffer, Long> {

    @Query("select o from MedicationOffer o where o.medicationOrder.id = ?1")
    Collection<MedicationOffer> getMedicationOffersByMedicationOrder(Long medicationOrderId);
}
