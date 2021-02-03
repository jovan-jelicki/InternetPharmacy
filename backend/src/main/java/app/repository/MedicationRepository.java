package app.repository;

import app.model.medication.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    @Query("select m from Medication m where m.name = ?1")

    Medication getMedicationByName(String name);

}
