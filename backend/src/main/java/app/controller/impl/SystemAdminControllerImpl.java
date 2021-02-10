package app.controller.impl;

import app.dto.UserPasswordDTO;
import app.model.user.Patient;
import app.model.user.SystemAdmin;
import app.service.SystemAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "api/systemAdmin")
public class SystemAdminControllerImpl {
    private final SystemAdminService systemAdminService;

    public SystemAdminControllerImpl(SystemAdminService systemAdminService) { this.systemAdminService = systemAdminService; }

    @PreAuthorize("hasRole('systemAdmin')")
    @PostMapping(value="/save", consumes = "application/json")
    public ResponseEntity<SystemAdmin> save(@RequestBody SystemAdmin entity) {
        return new ResponseEntity<>(systemAdminService.save(entity), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('systemAdmin')")
    @GetMapping(value = "/isAccountApproved/{id}")
    public ResponseEntity<Boolean> isAccountApproved(@PathVariable Long id){
        return new ResponseEntity<>(systemAdminService.read(id).get().getApprovedAccount(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('systemAdmin')")
    @PutMapping(value = "/pass")
    public ResponseEntity<Void> changePassword(@RequestBody UserPasswordDTO passwordKit) {
        try {
            systemAdminService.changePassword(passwordKit);
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
