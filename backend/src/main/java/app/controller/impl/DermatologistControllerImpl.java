package app.controller.impl;

import app.controller.DermatologistController;
import app.model.Dermatologist;
import app.model.Pharmacist;
import app.model.WorkingHours;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/dermatologists")
public class DermatologistControllerImpl implements DermatologistController {
    private UserService<Dermatologist> dermatologistUserService;

    @Autowired
    public DermatologistControllerImpl(UserService<Dermatologist> dermatologistUserService) {
        this.dermatologistUserService = dermatologistUserService;
    }

    @Override
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Dermatologist> save(Dermatologist entity) {
        return new ResponseEntity<>(dermatologistUserService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(consumes = "application/json")
    public ResponseEntity<Dermatologist> update(Dermatologist entity) {
        if(!dermatologistUserService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(dermatologistUserService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<Collection<Dermatologist>> read() {
        return new ResponseEntity<>(dermatologistUserService.read(), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Dermatologist>> read(Long id) {
        return new ResponseEntity<>(dermatologistUserService.read(id), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(Long id) {
        if(!dermatologistUserService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        dermatologistUserService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Collection<WorkingHours>> getDermatologistsWorkingHours(long id) {
        Dermatologist dermatologist = dermatologistUserService.read(id).get();
        return new ResponseEntity<>(dermatologist.getWorkingHours(), HttpStatus.OK);
    }
}
