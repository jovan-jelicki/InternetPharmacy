package app.repository;

import app.model.medication.MedicationReservation;
import app.model.medication.MedicationReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface MedicationReservationRepository extends JpaRepository<MedicationReservation, Long> {

    Collection<MedicationReservation> findAllByPatient_IdAndStatus(Long patientId, MedicationReservationStatus status);
}
