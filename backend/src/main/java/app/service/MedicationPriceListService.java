package app.service;

import app.model.medication.MedicationPriceList;

import java.time.LocalDateTime;

public interface MedicationPriceListService extends CRUDService<MedicationPriceList>{

    MedicationPriceList GetMedicationPriceInPharmacyByDate(Long pharmacyId, Long medicationId, LocalDateTime date);

}
