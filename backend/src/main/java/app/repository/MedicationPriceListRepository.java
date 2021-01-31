package app.repository;

import app.model.medication.MedicationPriceList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationPriceListRepository extends JpaRepository<MedicationPriceList, Long> {
}
