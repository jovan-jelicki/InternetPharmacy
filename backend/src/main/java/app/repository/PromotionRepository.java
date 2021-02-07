package app.repository;

import app.model.pharmacy.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Query("select p from Promotion p where p.pharmacy.id = ?1 and p.period.periodStart<= ?2 and p.period.periodEnd >=?2")
    Collection<Promotion> getCurrentPromotionsByPharmacyAndDate(Long pharmacyId, LocalDateTime date);

    @Query("select p from Promotion p where p.pharmacy.id = ?1")
    Collection<Promotion> getPromotionByPharmacy(Long pharmacyId);
}
