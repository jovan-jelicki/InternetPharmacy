package app.controller.impl;
import app.model.pharmacy.LoyaltyProgram;
import app.model.pharmacy.LoyaltyScale;
import app.service.LoyaltyProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "api/loyaltyProgram")
public class LoyaltyProgramControllerImpl {
    private final LoyaltyProgramService loyaltyProgramService;

    public LoyaltyProgramControllerImpl(LoyaltyProgramService loyaltyProgramService) {
        this.loyaltyProgramService = loyaltyProgramService;
    }

    @PostMapping(value="/save", consumes = "application/json")
    public ResponseEntity<Boolean> saveProgram(@RequestBody LoyaltyProgram entity) {
        return new ResponseEntity<>(loyaltyProgramService.saveProgram(entity), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<LoyaltyProgram>> read() {
        return new ResponseEntity<>(loyaltyProgramService.read(), HttpStatus.OK);
    }

}
