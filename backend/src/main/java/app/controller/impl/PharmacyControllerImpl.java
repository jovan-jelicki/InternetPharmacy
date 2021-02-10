package app.controller.impl;

import app.dto.*;
import app.model.grade.GradeType;
import app.model.medication.EPrescription;
import app.model.pharmacy.Pharmacy;
import app.service.GradeService;
import app.service.PharmacyService;
import app.util.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping(value = "api/pharmacy")
public class PharmacyControllerImpl {

    private final PharmacyService pharmacyService;
    private final GradeService gradeService;

    @Autowired
    public PharmacyControllerImpl(PharmacyService pharmacyService, GradeService gradeService) {
        this.pharmacyService = pharmacyService;
        this.gradeService = gradeService;
    }

    @GetMapping
    public ResponseEntity<Collection<PharmacyDTO>> read() {
        ArrayList<PharmacyDTO> pharmacyDTOS = new ArrayList<>();
        for (Pharmacy pharmacy : pharmacyService.read()) {
            PharmacyDTO pharmacyDTO = new PharmacyDTO(pharmacy);
            pharmacyDTO.setGrade(gradeService.findAverageGradeForEntity(pharmacy.getId(), GradeType.pharmacy));
            pharmacyDTOS.add(pharmacyDTO);
        }
        return new ResponseEntity<>(pharmacyDTOS, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", value="save")
    public ResponseEntity<Pharmacy> savePharmacy(@RequestBody PharmacyAdminPharmacyDTO pharmacy) {
        return new ResponseEntity<>(pharmacyService.savePharmacy(pharmacy), HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<PharmacyDTO> read(@PathVariable Long id) {
        return new ResponseEntity<>(new PharmacyDTO(pharmacyService.read(id).get(), gradeService.findAverageGradeForEntity(id, GradeType.pharmacy)), HttpStatus.OK);
    }

    @PostMapping(value = "/search")
    public ResponseEntity<Collection<Pharmacy>> search(@RequestBody PharmacySearchDTO pharmacySearchDTO) {
        return new ResponseEntity<>(pharmacyService.searchByNameAndAddress(pharmacySearchDTO), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Pharmacy> saveCourse(@RequestBody Pharmacy pharmacy) {
        Pharmacy a = pharmacy;
//        Pha course = new Course();
//        course.setName(courseDTO.getName());
//
//        course = courseService.save(course);
//        return new ResponseEntity<>(new CourseDTO(course), HttpStatus.CREATED);
        return null;
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<PharmacyDTO> update(@RequestBody Pharmacy entity) {
        if(!pharmacyService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new PharmacyDTO(pharmacyService.save(entity)), HttpStatus.CREATED);
    }

    @PutMapping(value = "/editPharmacyProfile", consumes = "application/json")
    public ResponseEntity<PharmacyDTO> editPharmacyProfile(@DTO(PharmacyDTO.class) Pharmacy pharmacy) {
        if(!pharmacyService.existsById(pharmacy.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new PharmacyDTO(pharmacyService.save(pharmacy)), HttpStatus.CREATED);
    }

    @PostMapping(value = "/dto")
    public void newPharmacyDTOMapping(@DTO(PharmacyDTO.class) Pharmacy pharmacy) {
        pharmacyService.save(pharmacy);
    }

    @PutMapping(value = "/addNewMedication", consumes = "application/json")
    public ResponseEntity<Boolean> addNewMedication(@RequestBody AddMedicationToPharmacyDTO addMedicationToPharmacyDTO) {
        if(!pharmacyService.existsById(addMedicationToPharmacyDTO.getPharmacyId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (pharmacyService.addNewMedication(addMedicationToPharmacyDTO))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/getPharmacyMedicationListing/{pharmacyId}")
    public ResponseEntity<Collection<PharmacyMedicationListingDTO>> getPharmacyMedicationListing(@PathVariable Long pharmacyId) {
        return new ResponseEntity<>(pharmacyService.getPharmacyMedicationListingDTOs(pharmacyId), HttpStatus.OK);
    }

    @PutMapping(value = "/editMedicationQuantity", consumes = "application/json")
    public ResponseEntity<Boolean> editMedicationQuantity(@RequestBody PharmacyMedicationListingDTO pharmacyMedicationListingDTO) {
        if(!pharmacyService.existsById(pharmacyMedicationListingDTO.getPharmacyId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (pharmacyService.editMedicationQuantity(pharmacyMedicationListingDTO))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/getPharmacyByMedication/{medicationId}")
    public ResponseEntity<Collection<PharmacyMedicationDTO>> getPharmacyByMedication(@PathVariable Long medicationId) {
        pharmacyService.getPharmacyByMedication(medicationId);
        return new ResponseEntity<>(pharmacyService.getPharmacyByMedication(medicationId), HttpStatus.OK);
    }


    @PutMapping(value = "/deleteMedicationFromPharmacy", consumes = "application/json")
    public ResponseEntity<Boolean> deleteMedicationFromPharmacy(@RequestBody PharmacyMedicationListingDTO pharmacyMedicationListingDTO) {
        if(!pharmacyService.existsById(pharmacyMedicationListingDTO.getPharmacyId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (pharmacyService.deleteMedicationFromPharmacy(pharmacyMedicationListingDTO))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/getMedicationsConsumptionMonthlyReport/{pharmacyId}")
    public ResponseEntity<Collection<ReportsDTO>> getMedicationsConsumptionMonthlyReport(@PathVariable Long pharmacyId) {
        return new ResponseEntity(pharmacyService.getMedicationsConsumptionMonthlyReport(pharmacyId), HttpStatus.OK);
    }

    @GetMapping(value = "/getMedicationsConsumptionQuarterlyReport/{pharmacyId}")
    public ResponseEntity<Collection<ReportsDTO>> getMedicationsConsumptionQuarterlyReport(@PathVariable Long pharmacyId) {
        return new ResponseEntity(pharmacyService.getMedicationsConsumptionQuarterlyReport(pharmacyId), HttpStatus.OK);
    }

    @GetMapping(value = "/getMedicationsConsumptionYearlyReport/{pharmacyId}")
    public ResponseEntity<Collection<ReportsDTO>> getMedicationsConsumptionYearlyReport(@PathVariable Long pharmacyId) {
        return new ResponseEntity(pharmacyService.getMedicationsConsumptionYearlyReport(pharmacyId), HttpStatus.OK);
    }

    @PostMapping(value = "/getPharmacyIncomeReportByPeriod", consumes = "application/json")
    public ResponseEntity<Collection<ReportsDTO>> getPharmacyIncomeReportByPeriod(@RequestBody PharmacyIncomeReportDTO pharmacyIncomeReportDTO) {
        if (!pharmacyService.existsById(pharmacyIncomeReportDTO.getPharmacyId()) || pharmacyIncomeReportDTO.getPeriodStart().isAfter(pharmacyIncomeReportDTO.getPeriodEnd()))
            return new ResponseEntity(null,HttpStatus.BAD_REQUEST);
        return new ResponseEntity(pharmacyService.getPharmacyIncomeReportByPeriod(pharmacyIncomeReportDTO.getPeriodStart(),pharmacyIncomeReportDTO.getPeriodEnd(), pharmacyIncomeReportDTO.getPharmacyId()), HttpStatus.OK);
    }

}
