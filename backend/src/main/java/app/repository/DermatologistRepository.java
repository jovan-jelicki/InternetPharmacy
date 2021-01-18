package app.repository;

import app.model.Dermatologist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DermatologistRepository extends JpaRepository<Dermatologist, Long> {
}
