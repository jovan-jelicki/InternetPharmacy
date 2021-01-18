package app.repository;

import app.model.user.PharmacyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyAdminRepository extends JpaRepository<PharmacyAdmin, Long> {
}
