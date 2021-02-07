package app.controller.impl;


import app.dto.PromotionDTO;
import app.service.PromotionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping(value = "api/promotion")
public class PromotionController {
    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping
    public ResponseEntity<Collection<PromotionDTO>> read() {
        ArrayList<PromotionDTO> promotionDTOS = new ArrayList<>();
        promotionService.read().forEach(promotion -> promotionDTOS.add(new PromotionDTO(promotion)));
        return new ResponseEntity<>(promotionDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/getCurrentPromotionsByPharmacy/{pharmacyId}")
    public ResponseEntity<Collection<PromotionDTO>> getCurrentPromotionsByPharmacy(@PathVariable Long pharmacyId) {
        ArrayList<PromotionDTO> promotionDTOS = new ArrayList<>();
        promotionService.getCurrentPromotionsByPharmacyAndDate(pharmacyId, LocalDateTime.now()).forEach(promotion -> promotionDTOS.add(new PromotionDTO(promotion)));
        return new ResponseEntity<>(promotionDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/checkPatientSubscribedToPromotion/{pharmacyId}/{patientId}/{medicationId}")
    public ResponseEntity<Boolean> checkPatientSubscribedToPromotion(@PathVariable Long pharmacyId, @PathVariable Long patientId,@PathVariable Long medicationId) {
        return new ResponseEntity<>(promotionService.checkPatientSubscribedToPromotion(pharmacyId, patientId, medicationId), HttpStatus.OK);
    }

    @PutMapping(value = "/subscribeToPromotion/{pharmacyId}/{patientId}/{promotionId}")
    public ResponseEntity<Boolean> subscribeToPromotion(@PathVariable Long patientId,@PathVariable Long promotionId) {
        if (promotionService.subscribeToPromotion(patientId, promotionId))
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
}
