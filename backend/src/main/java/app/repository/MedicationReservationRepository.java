package app.repository;

import app.dto.GetMedicationReservationDTO;
import app.model.medication.MedicationReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicationReservationRepository extends JpaRepository<MedicationReservation, Long> {
}
