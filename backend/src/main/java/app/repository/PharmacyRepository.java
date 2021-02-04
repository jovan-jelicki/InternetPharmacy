package app.repository;

import app.model.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
