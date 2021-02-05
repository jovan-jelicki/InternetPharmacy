package app.controller.impl;

import app.dto.MedicationOfferDTO;
import app.service.MedicationOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "api/medicationOffer")
public class MedicationOfferControllerImpl {
    private final MedicationOfferService medicationOfferService;

    public MedicationOfferControllerImpl(MedicationOfferService medicationOfferService) {
        this.medicationOfferService = medicationOfferService;
    }

    @PostMapping(consumes = "application/json", value = "/new")
    public ResponseEntity<Boolean> createNewMedicationOffer(@RequestBody MedicationOfferDTO medicationOffer){
        return new ResponseEntity<>(medicationOfferService.createNewMedicationOffer(medicationOffer), HttpStatus.OK);
    }

    /*
    @GetMapping(value = "/getAllBySupplier")
    public ResponseEntity<Collection<MedicationOffer>> getMedicationOfferBySupplier(@RequestBody Long supplierId) {
        return new ResponseEntity<>(medicationOfferService.getMedicationOfferBySupplier(supplierId), HttpStatus.OK);
    }
    */

}
