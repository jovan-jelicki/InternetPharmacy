package app.controller.impl;

import app.controller.DermatologistController;
import app.dto.PharmacyNameIdDTO;
import app.dto.DermatologistDTO;
import app.dto.UserPasswordDTO;
import app.model.time.WorkingHours;
import app.model.user.Dermatologist;
import app.service.DermatologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    @PostMapping(consumes = "application/json")
    public ResponseEntity<Dermatologist> save(@RequestBody Dermatologist entity) {
        return new ResponseEntity<>(dermatologistService.save(entity), HttpStatus.CREATED);
    }


    @PutMapping(consumes = "application/json")
    public ResponseEntity<Dermatologist> update(@RequestBody Dermatologist entity) {
        if(!dermatologistService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(dermatologistService.save(entity), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<ArrayList<DermatologistDTO>> read() {
        ArrayList<DermatologistDTO> dermatologistDTOS = new ArrayList<>();
        for (Dermatologist dermatologist : dermatologistService.read())
            dermatologistDTOS.add(new DermatologistDTO(dermatologist));
        return new ResponseEntity<>(dermatologistDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/getPharmacy/{id}")
    public ResponseEntity<Collection<PharmacyNameIdDTO>> getPharmacyOfPharmacist(@PathVariable Long id){
        return new ResponseEntity<>(dermatologistService.getPharmacyOfPharmacist(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Dermatologist>> read(@PathVariable Long id) {
        return new ResponseEntity<>(dermatologistService.read(id), HttpStatus.OK);
    }


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
    public ResponseEntity<Collection<DermatologistDTO>> getAllDermatologistNotWorkingInPharmacy(@PathVariable Long id) {
        ArrayList<DermatologistDTO> dermatologistDTOS = new ArrayList<>();
        for (Dermatologist dermatologist : dermatologistService.getAllDermatologistNotWorkingInPharmacy(id))
            dermatologistDTOS.add(new DermatologistDTO(dermatologist));
        return new ResponseEntity<>(dermatologistDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllDermatologistWorkingInPharmacy/{id}")
    public ResponseEntity<Collection<DermatologistDTO>> getAllDermatologistWorkingInPharmacy(@PathVariable Long id) {
        ArrayList<DermatologistDTO> dermatologistDTOS = new ArrayList<>();
        for (Dermatologist dermatologist : dermatologistService.getAllDermatologistWorkingInPharmacy(id))
            dermatologistDTOS.add(new DermatologistDTO(dermatologist));
        return new ResponseEntity<>(dermatologistDTOS, HttpStatus.OK);
    }

}
