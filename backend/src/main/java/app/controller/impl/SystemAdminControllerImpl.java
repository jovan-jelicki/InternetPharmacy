package app.controller.impl;

import app.model.user.Patient;
import app.model.user.SystemAdmin;
import app.service.SystemAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "api/systemAdmin")
public class SystemAdminControllerImpl {
    private final SystemAdminService systemAdminService;

    public SystemAdminControllerImpl(SystemAdminService systemAdminService) { this.systemAdminService = systemAdminService; }

    @PostMapping(value="/save", consumes = "application/json")
    public ResponseEntity<SystemAdmin> save(@RequestBody SystemAdmin entity) {
        return new ResponseEntity<>(systemAdminService.save(entity), HttpStatus.CREATED);
    }
}
