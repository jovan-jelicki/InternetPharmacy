package app.controller.impl;

import app.dto.MedicationSearchDTO;
import app.model.medication.Medication;
import app.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/medications")
public class MedicationControllerImpl {
    private final MedicationService medicationService;

    @Autowired
    public MedicationControllerImpl(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Medication> save(@RequestBody Medication entity) {
        return new ResponseEntity<>(medicationService.save(entity), HttpStatus.CREATED);
    }

    @PostMapping(value = "/search")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Collection<Medication>> search(@RequestBody MedicationSearchDTO medicationName) {
        return new ResponseEntity<>(medicationService.getMedicationByNameIsContaining(medicationName),HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Collection<Medication>> read() {
        return new ResponseEntity<>(medicationService.read(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Medication>> read(@PathVariable Long id) {
        return new ResponseEntity<>(medicationService.read(id), HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<Medication> update(@RequestBody Medication entity) {
        if(!medicationService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(medicationService.save(entity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!medicationService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        medicationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacist, dermatologist')")
    @GetMapping(value = "/getMedicationsForPatient/{id}")
    public ResponseEntity<Collection<Medication>> getAllMedicationsPatientIsNotAllergicTo(@PathVariable Long id){
        return new ResponseEntity<>(medicationService.getAllMedicationsPatientIsNotAllergicTo(id), HttpStatus.OK);
    }

    @GetMapping(value = "/alternatives/{id}")
    public ResponseEntity<Collection<Medication>> getMedicationAlternatives(@PathVariable Long id) {
        return new ResponseEntity<>(medicationService.fetchMedicationAlternatives(id), HttpStatus.OK);
    }

    @GetMapping(value = "/getMedicationsNotContainedInPharmacy/{pharmacyId}")
    public ResponseEntity<Collection<Medication>> getMedicationsNotContainedInPharmacy(@PathVariable Long pharmacyId){
        return new ResponseEntity<>(medicationService.getMedicationsNotContainedInPharmacy(pharmacyId), HttpStatus.OK);
    }


}
