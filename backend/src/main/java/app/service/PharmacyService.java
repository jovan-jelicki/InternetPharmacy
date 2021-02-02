package app.service;

import app.dto.AddMedicationToPharmacyDTO;
import app.dto.PharmacyMedicationListingDTO;
import app.dto.PharmacySearchDTO;
import app.model.medication.MedicationQuantity;
import app.model.pharmacy.Pharmacy;
import app.service.impl.MedicationPriceListServiceImpl;

import java.util.Collection;

public interface PharmacyService extends CRUDService<Pharmacy> {
    Collection<Pharmacy> searchByNameAndAddress(PharmacySearchDTO pharmacySearchDTO);
    Boolean checkMedicationQuantity(Collection<MedicationQuantity> medicationQuantities, Pharmacy pharmacy);

    Boolean addNewMedication(AddMedicationToPharmacyDTO addMedicationToPharmacyDTO);

    void setMedicationService(MedicationService medicationService);

    Collection<PharmacyMedicationListingDTO> getPharmacyMedicationListingDTOs(Long pharmacyId);

    Boolean editMedicationQuantity(PharmacyMedicationListingDTO pharmacyMedicationListingDTO);

    void setMedicationPriceListService(MedicationPriceListServiceImpl medicationPriceListService);

    Collection<Pharmacy> getPharmacyByMedication(Long medicationId);
}
