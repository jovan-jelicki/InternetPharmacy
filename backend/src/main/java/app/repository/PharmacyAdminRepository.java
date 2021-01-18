package app.repository;

import app.model.PharmacyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyAdminRepository extends JpaRepository<PharmacyAdmin, Long> {
}
