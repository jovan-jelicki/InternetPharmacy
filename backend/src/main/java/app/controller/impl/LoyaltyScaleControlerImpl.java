package app.controller.impl;

import app.model.pharmacy.LoyaltyScale;
import app.model.user.Patient;
import app.service.LoyaltyScaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/loyaltyScale")
public class LoyaltyScaleControlerImpl {
    private final LoyaltyScaleService loyaltyScaleService;

    public LoyaltyScaleControlerImpl(LoyaltyScaleService loyaltyScaleService) {
        this.loyaltyScaleService = loyaltyScaleService;
    }

    @PostMapping(value="/save", consumes = "application/json")
    public ResponseEntity<LoyaltyScale> save(@RequestBody LoyaltyScale entity) {
        return new ResponseEntity<>(loyaltyScaleService.save(entity), HttpStatus.CREATED);
    }
}
