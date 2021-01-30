package app.repository;

import app.model.user.SystemAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SystemAdminRepository extends JpaRepository<SystemAdmin, Long> {
    @Query("select s from SystemAdmin s where s.credentials.email =?1 and s.credentials.password=?2")
    SystemAdmin findByEmailAndPassword(String email,String password);
}
