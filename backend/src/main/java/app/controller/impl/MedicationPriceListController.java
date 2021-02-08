package app.controller.impl;

import app.dto.MedicationPriceListDTO;
import app.service.MedicationPriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping(value = "api/pricelist")
public class MedicationPriceListController {
    private final MedicationPriceListService medicationPriceListService;

    @Autowired
    public MedicationPriceListController(MedicationPriceListService medicationPriceListService) {
        this.medicationPriceListService = medicationPriceListService;
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin')")
    @GetMapping(value = "/getCurrentMedicationPriceListByPharmacy/{pharmacyId}")
    public ResponseEntity<Collection<MedicationPriceListDTO>> read(@PathVariable Long pharmacyId) {
        return new ResponseEntity<>(medicationPriceListService.getCurrentPriceListsByPharmacy(pharmacyId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin')")
    @GetMapping(value = "/getMedicationPriceListHistoryByPharmacy/{pharmacyId}/{medicationId}")
    public ResponseEntity<Collection<MedicationPriceListDTO>> read(@PathVariable Long pharmacyId, @PathVariable Long medicationId) {
        return new ResponseEntity<>(medicationPriceListService.getMedicationPriceListHistoryByPharmacy(pharmacyId, medicationId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin')")
    @PutMapping(value = "/newPriceList", consumes = "application/json")
    public ResponseEntity<Boolean> read(@RequestBody MedicationPriceListDTO medicationPriceListDTO) {
        if (medicationPriceListService.createNewPriceList(medicationPriceListDTO))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
