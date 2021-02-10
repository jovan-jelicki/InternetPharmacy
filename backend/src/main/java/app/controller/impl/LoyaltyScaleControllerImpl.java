package app.controller.impl;

import app.model.pharmacy.LoyaltyScale;
import app.service.LoyaltyScaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "api/loyaltyScale")
public class LoyaltyScaleControllerImpl {
    private final LoyaltyScaleService loyaltyScaleService;

    public LoyaltyScaleControllerImpl(LoyaltyScaleService loyaltyScaleService) {
        this.loyaltyScaleService = loyaltyScaleService;
    }


    @PreAuthorize("hasRole('systemAdmin')")
    @PostMapping(value="/save", consumes = "application/json")
    public ResponseEntity<Boolean> save(@RequestBody LoyaltyScale entity) {
        return new ResponseEntity<>(loyaltyScaleService.editLoyaltyScale(entity), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('systemAdmin')")
    @GetMapping
    public ResponseEntity<Collection<LoyaltyScale>> read() {
        return new ResponseEntity<>(loyaltyScaleService.read(), HttpStatus.OK);
    }
}
