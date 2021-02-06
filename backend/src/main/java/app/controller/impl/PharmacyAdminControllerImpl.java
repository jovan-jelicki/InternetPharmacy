package app.controller.impl;

import app.dto.PharmacyAdminDTO;
import app.dto.UserPasswordDTO;
import app.model.pharmacy.Pharmacy;
import app.model.user.PharmacyAdmin;
import app.service.PharmacyAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "api/pharmacyAdmin")
public class PharmacyAdminControllerImpl {
    private final PharmacyAdminService pharmacyAdminService;

    @Autowired
    public PharmacyAdminControllerImpl(PharmacyAdminService pharmacyAdminService) {
        this.pharmacyAdminService = pharmacyAdminService;
    }

    @GetMapping
    public ResponseEntity<List<PharmacyAdminDTO>> read() {

        ArrayList<PharmacyAdminDTO> pharmacyAdminDTOS = new ArrayList<PharmacyAdminDTO>();

        for (PharmacyAdmin pharmacyAdmin : (List<PharmacyAdmin>) pharmacyAdminService.read()) {
            pharmacyAdminDTOS.add(new PharmacyAdminDTO(pharmacyAdmin));
        }

        return new ResponseEntity<>(pharmacyAdminDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PharmacyAdminDTO> read(@PathVariable Long id) {
        Optional<PharmacyAdmin> pharmacyAdmin = this.pharmacyAdminService.read(id);
        if (!pharmacyAdmin.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new PharmacyAdminDTO(pharmacyAdmin.get()), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PharmacyAdminDTO> save(@RequestBody PharmacyAdmin pharmacyAdmin) {
        return new ResponseEntity<>(new PharmacyAdminDTO(this.pharmacyAdminService.save(pharmacyAdmin)), HttpStatus.CREATED);
    }

    @PostMapping(consumes = "application/json", value="save")
    public ResponseEntity<PharmacyAdmin> saveAdmin(@RequestBody PharmacyAdmin pharmacyAdmin) {
        return new ResponseEntity<>(this.pharmacyAdminService.save(pharmacyAdmin), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<PharmacyAdmin> pharmacyAdmin = this.pharmacyAdminService.read(id);

        if (pharmacyAdmin.isPresent()) {
            this.pharmacyAdminService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping(consumes = "application/json")
    public ResponseEntity<PharmacyAdmin> update(@RequestBody PharmacyAdmin entity) {
        if(!pharmacyAdminService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        entity.getCredentials().setPassword(pharmacyAdminService.read(entity.getId()).get().getCredentials().getPassword());
        PharmacyAdmin e2 = pharmacyAdminService.read(entity.getId()).get();
        Pharmacy ew = e2.getPharmacy();
        entity.setPharmacy(pharmacyAdminService.read(entity.getId()).get().getPharmacy());
        return new ResponseEntity<>(pharmacyAdminService.save(entity), HttpStatus.CREATED);
    }


    @PutMapping(value = "/pass")
    public ResponseEntity<Void> changePassword(@RequestBody UserPasswordDTO passwordKit) {
        try {
            pharmacyAdminService.changePassword(passwordKit);
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
