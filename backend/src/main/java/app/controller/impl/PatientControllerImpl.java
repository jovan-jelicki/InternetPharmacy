package app.controller.impl;

import app.controller.PatientController;
import app.model.Patient;
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
    private UserService<Patient> patientUserService;

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
}
