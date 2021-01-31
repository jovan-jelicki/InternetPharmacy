package app.repository;

import app.model.user.PharmacyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PharmacyAdminRepository extends JpaRepository<PharmacyAdmin, Long> {
    @Query("select p from PharmacyAdmin p where p.credentials.email =?1 and p.credentials.password=?2")
    PharmacyAdmin findByEmailAndPassword(String email, String password);

    @Query("select d from PharmacyAdmin d where d.credentials.email = ?1")
    PharmacyAdmin findByEmail(String email);
}
