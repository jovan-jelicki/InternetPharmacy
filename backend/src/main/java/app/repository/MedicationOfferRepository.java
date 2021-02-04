package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import app.model.medication.MedicationOffer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

public interface MedicationOfferRepository extends JpaRepository<MedicationOffer, Long> {
}
