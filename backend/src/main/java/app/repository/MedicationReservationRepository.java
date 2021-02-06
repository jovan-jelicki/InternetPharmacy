package app.repository;

import app.model.medication.MedicationReservation;
import app.model.medication.MedicationReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface MedicationReservationRepository extends JpaRepository<MedicationReservation, Long> {

    Collection<MedicationReservation> findAllByPatient_IdAndStatus(Long patientId, MedicationReservationStatus status);
}
