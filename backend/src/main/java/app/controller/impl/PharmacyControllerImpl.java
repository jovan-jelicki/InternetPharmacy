package app.controller.impl;

import app.dto.AddMedicationToPharmacyDTO;
import app.dto.PharmacyDTO;
import app.dto.PharmacyMedicationListingDTO;
import app.dto.PharmacySearchDTO;
import app.model.pharmacy.Pharmacy;
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

    @Autowired
    public PharmacyControllerImpl(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    @GetMapping
    public ResponseEntity<Collection<PharmacyDTO>> read() {
        ArrayList<PharmacyDTO> pharmacyDTOS = new ArrayList<>();
        for (Pharmacy pharmacy : pharmacyService.read())
            pharmacyDTOS.add(new PharmacyDTO(pharmacy));
        return new ResponseEntity<>(pharmacyDTOS, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<PharmacyDTO> read(@PathVariable Long id) {
        return new ResponseEntity<>(new PharmacyDTO(pharmacyService.read(id).get()), HttpStatus.OK);
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

}
