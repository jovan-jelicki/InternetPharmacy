package app.controller.impl;

import app.dto.UserPasswordDTO;
import app.model.time.WorkingHours;
import app.model.user.Pharmacist;
import app.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping(value = "api/pharmacist")
public class PharmacistControllerImpl {
    private final PharmacistService pharmacistService;

    @Autowired
    public PharmacistControllerImpl(PharmacistService pharmacistService) {
        this.pharmacistService = pharmacistService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Pharmacist> save(@RequestBody Pharmacist entity) {
        return new ResponseEntity<>(pharmacistService.save(entity), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<Pharmacist> update(@RequestBody Pharmacist entity) {
        if(!pharmacistService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(pharmacistService.save(entity), HttpStatus.CREATED);
    }


    @PutMapping(value = "/pass")
    public ResponseEntity<Void> changePassword(@RequestBody UserPasswordDTO passwordKit) {
        try {
            pharmacistService.changePassword(passwordKit);
        }
        catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<Pharmacist>> read() {
        Collection<Pharmacist> pharmacists = pharmacistService.read();
        for (Pharmacist pharmacist : pharmacists) {
            pharmacist.getWorkingHours().getPharmacy().setMedicationReservation(null);
            pharmacist.getWorkingHours().getPharmacy().setMedicationQuantity(null);
        }
        return new ResponseEntity<>(pharmacists, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Pharmacist>> read(@PathVariable Long id) {
        return new ResponseEntity<>(pharmacistService.read(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!pharmacistService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        pharmacistService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "getWorkingHours/{id}")
    public ResponseEntity<WorkingHours> getPharmacistsWorkingHours(@PathVariable Long id) {
        Pharmacist pharmacist = pharmacistService.read(id).get();
        return new ResponseEntity<>(pharmacist.getWorkingHours(), HttpStatus.OK);
    }

    @GetMapping(value = "getByPharmacy/{id}")
    public ResponseEntity<Collection<Pharmacist>> getPharmacistsByPharmacyId(@PathVariable Long id) {
        return new ResponseEntity<>(pharmacistService.getPharmacistsByPharmacyId(id), HttpStatus.OK);
    }
}
