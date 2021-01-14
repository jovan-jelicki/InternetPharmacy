package app.repository;

import app.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
