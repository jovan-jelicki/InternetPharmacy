package app.controller.impl;

import app.dto.MedicationPriceListDTO;
import app.service.MedicationPriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping(value = "api/pricelist")
public class MedicationPriceListController {
    private final MedicationPriceListService medicationPriceListService;

    @Autowired
    public MedicationPriceListController(MedicationPriceListService medicationPriceListService) {
        this.medicationPriceListService = medicationPriceListService;
    }

    @GetMapping(value = "/getCurrentMedicationPriceListByPharmacy/{pharmacyId}")
    public ResponseEntity<Collection<MedicationPriceListDTO>> read(@PathVariable Long pharmacyId) {
        return new ResponseEntity<>(medicationPriceListService.getCurrentPriceListsByPharmacy(pharmacyId), HttpStatus.OK);
    }

    @GetMapping(value = "/getMedicationPriceListHistoryByPharmacy/{pharmacyId}/{medicationId}")
    public ResponseEntity<Collection<MedicationPriceListDTO>> read(@PathVariable Long pharmacyId, @PathVariable Long medicationId) {
        return new ResponseEntity<>(medicationPriceListService.getMedicationPriceListHistoryByPharmacy(pharmacyId, medicationId), HttpStatus.OK);
    }
}
