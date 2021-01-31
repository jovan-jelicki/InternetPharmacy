package app.repository;

import app.model.user.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
    @Query("select p from Pharmacist p where p.credentials.email =?1 and p.credentials.password=?2 and p.isActive=true")
    Pharmacist findByEmailAndPassword(String email, String password);

}
