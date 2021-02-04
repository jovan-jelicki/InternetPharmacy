package app.controller.impl;

import app.dto.MedicationOfferAndOrderDTO;
import app.dto.MedicationOfferDTO;
import app.dto.PharmacyMedicationListingDTO;
import app.model.medication.MedicationOffer;
import app.model.user.Patient;
import app.model.user.Supplier;
import app.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping(value = "api/suppliers")
public class SupplierControllerImpl {
    private final SupplierService supplierService;

    public SupplierControllerImpl(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping(value = "/getAllBySupplier/{supplierId}")
    public ResponseEntity<Collection<MedicationOfferAndOrderDTO>> getMedicationOffersBySupplier(@PathVariable Long supplierId) {
        return new ResponseEntity<>(supplierService.getMedicationOffersBySupplier(supplierId), HttpStatus.OK);
    }

    @PostMapping(value="/save", consumes = "application/json")
    public ResponseEntity<Supplier> save(@RequestBody Supplier entity) {
        return new ResponseEntity<>(supplierService.save(entity), HttpStatus.CREATED);
    }


}
