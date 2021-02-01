package app.repository;

import app.model.medication.MedicationQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationQuantityRepository extends JpaRepository<MedicationQuantity, Long> {
}
