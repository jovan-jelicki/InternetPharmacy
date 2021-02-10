package app.controller.impl;

import app.dto.MedicationOfferAndOrderDTO;
import app.dto.MedicationOfferDTO;
import app.service.MedicationOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping(value = "api/medicationOffer")
public class MedicationOfferControllerImpl {
    private final MedicationOfferService medicationOfferService;

    public MedicationOfferControllerImpl(MedicationOfferService medicationOfferService) {
        this.medicationOfferService = medicationOfferService;
    }

    @PreAuthorize("hasAnyRole('supplier')")
    @PostMapping(consumes = "application/json", value = "/new")
    public ResponseEntity<Boolean> createNewMedicationOffer(@RequestBody MedicationOfferDTO medicationOffer){
        return new ResponseEntity<>(medicationOfferService.createNewMedicationOffer(medicationOffer), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('supplier')")
    @PostMapping(consumes = "application/json", value = "/edit")
    public ResponseEntity<Boolean> editMedicationOffer(@RequestBody MedicationOfferAndOrderDTO medicationOffer){
        return new ResponseEntity<>(medicationOfferService.editMedicationOffer(medicationOffer), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin')")
    @GetMapping(value = "/getOffersByOrderId/{orderId}")
    public ResponseEntity<Collection<MedicationOfferDTO>> getOffersByOrderId(@PathVariable Long orderId){
        return new ResponseEntity<>(medicationOfferService.getOffersByOrderId(orderId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin')")
    @PutMapping(consumes = "application/json", value = "/acceptOffer/{pharmacyAdminId}")
    public ResponseEntity<Boolean> acceptOffer(@RequestBody MedicationOfferDTO medicationOffer, @PathVariable Long pharmacyAdminId){
        if (medicationOfferService.acceptOffer(medicationOffer, pharmacyAdminId))
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
    /*
    @GetMapping(value = "/getAllBySupplier")
    public ResponseEntity<Collection<MedicationOffer>> getMedicationOfferBySupplier(@RequestBody Long supplierId) {
        return new ResponseEntity<>(medicationOfferService.getMedicationOfferBySupplier(supplierId), HttpStatus.OK);
    }
    */

}
