package app.controller.impl;

import app.dto.PharmacistDTO;
import app.dto.PharmacyNameIdDTO;
import app.dto.UserPasswordDTO;
import app.dto.WorkingHoursDTO;
import app.model.user.Pharmacist;
import app.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;


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

    @GetMapping(value = "/getPharmacy/{id}")
    public ResponseEntity<PharmacyNameIdDTO> getPharmacyOfPharmacist(@PathVariable Long id){
        return new ResponseEntity<>(pharmacistService.getPharmacyOfPharmacist(id), HttpStatus.OK);
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
    public ResponseEntity<Collection<PharmacistDTO>> read() {
        ArrayList<PharmacistDTO> pharmacists = new ArrayList<>();
        for (Pharmacist pharmacist : pharmacistService.read()) {
            pharmacists.add(new PharmacistDTO(pharmacist));
        }
        return new ResponseEntity<>(pharmacists, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PharmacistDTO> read(@PathVariable Long id) {
        return new ResponseEntity<>(new PharmacistDTO(pharmacistService.read(id).get()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!pharmacistService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else if (!pharmacistService.read(id).get().getActive())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        pharmacistService.delete(id);
        if (pharmacistService.read(id).get().getActive())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "getWorkingHours/{id}")
    public ResponseEntity<WorkingHoursDTO> getPharmacistsWorkingHours(@PathVariable Long id) {
        Pharmacist pharmacist = pharmacistService.read(id).get();
        return new ResponseEntity<>(new WorkingHoursDTO(pharmacist.getWorkingHours()), HttpStatus.OK);
    }

    @GetMapping(value = "getByPharmacy/{id}")
    public ResponseEntity<Collection<PharmacistDTO>> getPharmacistsByPharmacyId(@PathVariable Long id) {
        ArrayList<PharmacistDTO> pharmacistDTOS = new ArrayList<>();
        for (Pharmacist pharmacist : pharmacistService.getPharmacistsByPharmacyId(id))
            pharmacistDTOS.add(new PharmacistDTO(pharmacist));
        return new ResponseEntity<>(pharmacistDTOS, HttpStatus.OK);
    }
}
