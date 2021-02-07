package app.controller.impl;

import app.dto.PatientDTO;
import app.dto.PharmacyPlainDTO;
import app.dto.UserPasswordDTO;
import app.model.medication.Ingredient;
import app.model.pharmacy.Pharmacy;
import app.model.user.Patient;
import app.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping(value = "api/patients")
public class PatientControllerImpl {
    private final PatientService patientService;

    @Autowired
    public PatientControllerImpl(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping(value="/save", consumes = "application/json")
    public ResponseEntity<Patient> save(@RequestBody Patient entity) {
        return new ResponseEntity<>(patientService.save(entity), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('patient')")
    @PutMapping(consumes = "application/json")
    public ResponseEntity<PatientDTO> update(@RequestBody PatientDTO entity) {
        Optional<Patient> p = patientService.read(entity.getId());
        if(p.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Patient patient = p.get();
        entity.merge(patient);
        return new ResponseEntity<>(new PatientDTO(patientService.save(patient)), HttpStatus.CREATED);
    }

    //@PreAuthorize("hasRole('patient')")
    @GetMapping
    public ResponseEntity<Collection<Patient>> read() {
        return new ResponseEntity<>(patientService.read(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('patient')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PatientDTO> read(@PathVariable Long id) {
        return new ResponseEntity<>(new PatientDTO(patientService.read(id).get()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!patientService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        patientService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/allergies/{id}")
    public ResponseEntity<Collection<Ingredient>> getPatientAllergies(@PathVariable Long id) {
        Optional<Patient> patient = patientService.read(id);
        if(patient.isPresent())
            return new ResponseEntity<>(patient.get().getAllergies(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('patient')")
    @PutMapping(value = "/pass")
    public ResponseEntity<Void> changePassword(@RequestBody UserPasswordDTO passwordKit) {
        try {
            patientService.changePassword(passwordKit);
        }
        catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/promotion-pharmacies/{id}")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Collection<PharmacyPlainDTO>> getPromotionPharmacies(@PathVariable Long id) {
        return new ResponseEntity<>(patientService.getPromotionPharmacies(id), HttpStatus.OK);
    }
}
