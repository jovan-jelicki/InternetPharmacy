package app.repository;

import app.model.time.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {
    Collection<VacationRequest> findByPharmacyId(Long pharmacyId);
}
