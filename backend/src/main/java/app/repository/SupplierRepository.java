package app.repository;

import app.model.medication.MedicationOffer;
import app.model.user.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query("select s from Supplier s where s.credentials.email =?1 and s.credentials.password=?2")
    Supplier findByEmailAndPassword(String email,String password);

    @Query("select d from Supplier d where d.credentials.email = ?1")
    Supplier findByEmail(String email);

    @Query("select d from Supplier d where d.medicationOffer = ?1")
    Collection<MedicationOffer> getMedicationOffersBySupplier(Long supplierId);
}
