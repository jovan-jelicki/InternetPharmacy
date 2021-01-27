package app.controller.impl;

import app.controller.DermatologistController;
import app.dto.UserPasswordDTO;
import app.model.time.WorkingHours;
import app.model.user.Dermatologist;
import app.service.DermatologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/dermatologists")
public class DermatologistControllerImpl implements DermatologistController {
    private final DermatologistService dermatologistService;

    @Autowired
    public DermatologistControllerImpl(DermatologistService dermatologistService) {
        this.dermatologistService = dermatologistService;
    }

    @Override
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Dermatologist> save(@RequestBody Dermatologist entity) {
        return new ResponseEntity<>(dermatologistService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(consumes = "application/json")
    public ResponseEntity<Dermatologist> update(@RequestBody Dermatologist entity) {
        if(!dermatologistService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(dermatologistService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<Collection<Dermatologist>> read() {
        return new ResponseEntity<>(dermatologistService.read(), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Dermatologist>> read(@PathVariable Long id) {
        return new ResponseEntity<>(dermatologistService.read(id), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!dermatologistService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        dermatologistService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PutMapping(value = "/pass")
    public ResponseEntity<Void> changePassword(@RequestBody UserPasswordDTO passwordKit) {
        try {
            dermatologistService.changePassword(passwordKit);
        }
        catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Collection<WorkingHours>> getDermatologistsWorkingHours(long id) {
        Dermatologist dermatologist = dermatologistService.read(id).get();
        return new ResponseEntity<>(dermatologist.getWorkingHours(), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllDermatologistNotWorkingInPharmacy/{id}")
    public ResponseEntity<Collection<Dermatologist>> getAllDermatologistNotWorkingInPharmacy(@PathVariable Long id) {
        return new ResponseEntity<>(dermatologistService.getAllDermatologistNotWorkingInPharmacy(id), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllDermatologistWorkingInPharmacy/{id}")
    public ResponseEntity<Collection<Dermatologist>> getAllDermatologistWorkingInPharmacy(@PathVariable Long id) {
        return new ResponseEntity<>(dermatologistService.getAllDermatologistWorkingInPharmacy(id), HttpStatus.OK);
    }
}
