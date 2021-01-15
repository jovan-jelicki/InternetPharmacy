package app.controller.impl;

import app.dto.PharmacyAdminDTO;
import app.dto.SystemAdminDTO;
import app.model.PharmacyAdmin;
import app.model.SystemAdmin;
import app.service.UserService;
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
    private final UserService<PharmacyAdmin> userService;

    @Autowired
    public PharmacyAdminControllerImpl(UserService<PharmacyAdmin> userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<PharmacyAdminDTO>> read() {

        ArrayList<PharmacyAdminDTO> pharmacyAdminDTOS = new ArrayList<PharmacyAdminDTO>();

        for (PharmacyAdmin pharmacyAdmin : (List<PharmacyAdmin>) userService.read()) {
            pharmacyAdminDTOS.add(new PharmacyAdminDTO(pharmacyAdmin));
        }

        return new ResponseEntity<>(pharmacyAdminDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PharmacyAdminDTO> read(@PathVariable Long id) {
        Optional<PharmacyAdmin> pharmacyAdmin = this.userService.read(id);
        if (!pharmacyAdmin.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new PharmacyAdminDTO(pharmacyAdmin.get()), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PharmacyAdminDTO> save(@RequestBody PharmacyAdmin pharmacyAdmin) {
        return new ResponseEntity<>(new PharmacyAdminDTO(this.userService.save(pharmacyAdmin)), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<PharmacyAdmin> pharmacyAdmin = this.userService.read(id);

        if (pharmacyAdmin.isPresent()) {
            this.userService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<PharmacyAdminDTO> update(@RequestBody PharmacyAdmin pharmacyAdminUpdate) {
        Optional<PharmacyAdmin> pharmacyAdmin = this.userService.read(pharmacyAdminUpdate.getId());

        if (pharmacyAdmin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        pharmacyAdminUpdate.setId(pharmacyAdmin.get().getId());
        return new ResponseEntity<>(new PharmacyAdminDTO(this.userService.save(pharmacyAdminUpdate)), HttpStatus.OK);
    }
}
