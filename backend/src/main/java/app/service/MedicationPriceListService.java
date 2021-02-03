package app.service;

import app.dto.MedicationPriceListDTO;
import app.model.medication.MedicationPriceList;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MedicationPriceListService extends CRUDService<MedicationPriceList>{

    MedicationPriceList GetMedicationPriceInPharmacyByDate(Long pharmacyId, Long medicationId, LocalDateTime date);

    Collection<MedicationPriceListDTO> getCurrentPriceListsByPharmacy(Long pharmacyId);

    Collection<MedicationPriceListDTO> getMedicationPriceListHistoryByPharmacy(Long pharmacyId, Long medicationId);

    Boolean createNewPriceList(MedicationPriceListDTO medicationPriceListDTO);

    Double getMedicationPrice(Long pharmacyId, Long medicationId);
}
