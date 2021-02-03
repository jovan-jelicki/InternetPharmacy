package app.repository;

import app.model.medication.EPrescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface EPrescriptionRepository extends JpaRepository<EPrescription, Long> {

    Collection<EPrescription> findAllByPatient_Id(Long patientId);
}
