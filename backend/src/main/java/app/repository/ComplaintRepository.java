package app.repository;

import app.model.complaint.Complaint;
import app.model.medication.MedicationQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}
