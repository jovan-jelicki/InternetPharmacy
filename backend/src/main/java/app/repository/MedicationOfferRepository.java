package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.model.medication.MedicationOffer;

public interface MedicationOfferRepository extends JpaRepository<MedicationOffer, Long> {

}
