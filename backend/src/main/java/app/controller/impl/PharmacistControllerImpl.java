package app.controller.impl;

import app.controller.PharmacistController;
import app.model.Pharmacist;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping(value = "api/pharmacist")
public class PharmacistControllerImpl implements PharmacistController {
    private UserService<Pharmacist> pharmacistUserService;

    @Autowired
    public PharmacistControllerImpl(UserService<Pharmacist> pharmacistUserService) {
        this.pharmacistUserService = pharmacistUserService;
    }

    @Override
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Pharmacist> save(@RequestBody Pharmacist entity) {
        return new ResponseEntity<>(pharmacistUserService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(consumes = "application/json")
    public ResponseEntity<Pharmacist> update(Pharmacist entity) {
        if(!pharmacistUserService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(pharmacistUserService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<Collection<Pharmacist>> read() {
        return new ResponseEntity<>(pharmacistUserService.read(), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Pharmacist>> read(Long id) {
        return new ResponseEntity<>(pharmacistUserService.read(id), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(Long id) {
        if(!pharmacistUserService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        pharmacistUserService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
