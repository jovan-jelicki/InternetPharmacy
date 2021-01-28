//package app.controller.impl;
//
//import app.dto.SystemAdminDTO;
//import app.model.user.SystemAdmin;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping(value = "api/systemAdmin")
//public class SystemAdminControllerImpl {
//    private final UserService<SystemAdmin> userService;
//
//    @Autowired
//    public SystemAdminControllerImpl(UserService<SystemAdmin> userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<SystemAdminDTO>> read() {
//
//        ArrayList<SystemAdminDTO> systemAdminDTOS = new ArrayList<SystemAdminDTO>();
//
//        for (SystemAdmin systemAdmin : (List<SystemAdmin>) userService.read()) {
//            systemAdminDTOS.add(new SystemAdminDTO(systemAdmin));
//        }
//
//        return new ResponseEntity<>(systemAdminDTOS, HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/{id}")
//    public ResponseEntity<SystemAdminDTO> read(@PathVariable Long id) {
//        Optional<SystemAdmin> systemAdmin = this.userService.read(id);
//        if (!systemAdmin.isPresent()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(new SystemAdminDTO(systemAdmin.get()), HttpStatus.OK);
//    }
//
//    @PostMapping(consumes = "application/json")
//    public ResponseEntity<SystemAdminDTO> save(@RequestBody SystemAdmin systemAdmin) {
//        return new ResponseEntity<>(new SystemAdminDTO(this.userService.save(systemAdmin)), HttpStatus.CREATED);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        Optional<SystemAdmin> systemAdmin = this.userService.read(id);
//
//        if (systemAdmin.isPresent()) {
//            this.userService.delete(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping(consumes = "application/json")
//    public ResponseEntity<SystemAdminDTO> update(@RequestBody SystemAdmin systemAdminUpdate) {
//        Optional<SystemAdmin> systemAdmin = this.userService.read(systemAdminUpdate.getId());
//
//        if (systemAdmin.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        systemAdminUpdate.setId(systemAdmin.get().getId());
//        return new ResponseEntity<>(new SystemAdminDTO(this.userService.save(systemAdminUpdate)), HttpStatus.OK);
//    }
//
//}
