package app.repository;

import app.model.medication.MedicationLackingEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface MedicationLackingEventRepository extends JpaRepository<MedicationLackingEvent, Long> {

    Collection<MedicationLackingEvent> getMedicationLackingEventByPharmacyId(Long pharmacyId);
}
