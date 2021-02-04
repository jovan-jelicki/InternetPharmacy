package app.repository;

import app.model.medication.MedicationOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationOfferRepository extends JpaRepository<MedicationOffer, Long> {
}
