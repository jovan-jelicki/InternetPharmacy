package app.repository;

import app.model.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
}
