package app.repository;

import app.model.user.Dermatologist;
import app.model.user.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("select p from Patient p where p.credentials.email =?1 and p.credentials.password=?2")
    Patient findByEmailAndPassword(String email,String password);

    @Query("select d from Patient d where d.credentials.email = ?1")
    Patient findByEmail(String email);
}
