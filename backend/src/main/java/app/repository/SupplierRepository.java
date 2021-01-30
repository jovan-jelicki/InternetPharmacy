package app.repository;

import app.model.user.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query("select s from Supplier s where s.credentials.email =?1 and s.credentials.password=?2")
    Supplier findByEmailAndPassword(String email,String password);
}
