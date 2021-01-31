package app.repository;

import app.model.user.Dermatologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DermatologistRepository extends JpaRepository<Dermatologist, Long> {
    @Query("select d from Dermatologist d where d.credentials.email =?1 and d.credentials.password=?2")
    Dermatologist findByEmailAndPassword(String email, String password);

    @Query("select d from Dermatologist d where d.credentials.email = ?1")
    Dermatologist findByEmail(String email);
}
