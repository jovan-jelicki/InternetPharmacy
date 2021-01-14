package app.controller;

import app.dto.SystemAdminDTO;
import app.model.Pharmacy;
import app.model.SystemAdmin;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "api/systemAdmin")
public class SystemAdminController {
    private final UserService<SystemAdmin> userService;

    @Autowired
    public SystemAdminController(UserService<SystemAdmin> userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<SystemAdmin>> getAll() {
        List<SystemAdmin> admins = (List<SystemAdmin>) userService.read();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SystemAdminDTO> getOne(@PathVariable Long id) {
        Optional<SystemAdmin> systemAdmin = this.userService.read(id);
        if (!systemAdmin.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new SystemAdminDTO(systemAdmin.get()), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<SystemAdminDTO> saveSystemAdmin(@RequestBody SystemAdmin systemAdmin) {
        systemAdmin = this.userService.save(systemAdmin);
        return new ResponseEntity<>(new SystemAdminDTO(systemAdmin), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteSystemAdmin(@PathVariable Long id) {
        Optional<SystemAdmin> systemAdmin = this.userService.read(id);

        if (systemAdmin.isPresent()) {
            this.userService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<SystemAdminDTO> updateSystemAdmin(@RequestBody SystemAdmin systemAdminUpdate) {
        Optional<SystemAdmin> systemAdmin = this.userService.read(systemAdminUpdate.getId());

        if (systemAdmin.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        systemAdminUpdate.setId(systemAdmin.get().getId());
        this.userService.save(systemAdminUpdate);
        return new ResponseEntity<>(new SystemAdminDTO(systemAdminUpdate), HttpStatus.OK);
    }
}
