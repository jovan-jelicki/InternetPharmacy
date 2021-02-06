package app.service;

import app.dto.*;
import app.model.medication.MedicationQuantity;
import app.model.pharmacy.Pharmacy;
import app.service.impl.MedicationOfferServiceImpl;
import app.service.impl.MedicationPriceListServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Collection;

public interface PharmacyService extends CRUDService<Pharmacy> {
    Collection<Pharmacy> searchByNameAndAddress(PharmacySearchDTO pharmacySearchDTO);
    Boolean checkMedicationQuantity(Collection<MedicationQuantity> medicationQuantities, Pharmacy pharmacy);

    Boolean addNewMedication(AddMedicationToPharmacyDTO addMedicationToPharmacyDTO);

    void setMedicationService(MedicationService medicationService);

    Collection<PharmacyMedicationListingDTO> getPharmacyMedicationListingDTOs(Long pharmacyId);

    Boolean editMedicationQuantity(PharmacyMedicationListingDTO pharmacyMedicationListingDTO);

    void setMedicationPriceListService(MedicationPriceListServiceImpl medicationPriceListService);

    Boolean deleteMedicationFromPharmacy(PharmacyMedicationListingDTO pharmacyMedicationListingDTO);

    Collection<ReportsDTO> getMedicationsConsumptionMonthlyReport(Long pharmacyId);

    Collection<ReportsDTO> getMedicationsConsumptionQuarterlyReport(Long pharmacyId);

    Collection<ReportsDTO> getMedicationsConsumptionYearlyReport(Long pharmacyId);

    Collection<PharmacyMedicationDTO> getPharmacyByMedication(Long medicationId);

    Collection<ReportIncomeDTO> getPharmacyIncomeReportByPeriod(LocalDateTime periodStart, LocalDateTime periodEnd, Long pharmacyId);

    void setPromotionService(PromotionService promotionService);
    Pharmacy savePharmacy( PharmacyAdminPharmacyDTO pharmacy);

    void setMedicationOffer(MedicationOfferService medicationOfferService);
}
