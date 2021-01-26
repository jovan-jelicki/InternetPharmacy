package app.controller.impl;

import app.controller.PatientController;
import app.dto.UserPasswordDTO;
import app.model.medication.Ingredient;
import app.model.user.Patient;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/patients")
public class PatientControllerImpl implements PatientController {
    private final UserService<Patient> patientUserService;

    @Autowired
    public PatientControllerImpl(UserService<Patient> patientUserService) {
        this.patientUserService = patientUserService;
    }

    @Override
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Patient> save(@RequestBody Patient entity) {
        return new ResponseEntity<>(patientUserService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(consumes = "application/json")
    public ResponseEntity<Patient> update(@RequestBody Patient entity) {
        if(!patientUserService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(patientUserService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<Collection<Patient>> read() {
        return new ResponseEntity<>(patientUserService.read(), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Patient>> read(@PathVariable Long id) {
        return new ResponseEntity<>(patientUserService.read(id), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!patientUserService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        patientUserService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/allergies/{id}")
    public ResponseEntity<Collection<Ingredient>> getPatientAllergies(@PathVariable Long id) {
        Optional<Patient> patient = patientUserService.read(id);
        if(patient.isPresent())
            return new ResponseEntity<>(patient.get().getAllergies(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    @PutMapping(value = "/pass")
    public ResponseEntity<Void> changePassword(@RequestBody UserPasswordDTO passwordKit) {
        try {
            patientUserService.changePassword(passwordKit);
        }
        catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
