package app.repository;

import app.model.medication.EPrescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EPrescriptionRepository extends JpaRepository<EPrescription, Long> {
}
