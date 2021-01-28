package app.repository;

import app.model.medication.MedicationReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationReservationRepository extends JpaRepository<MedicationReservation, Long> {
}
