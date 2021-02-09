package app.controller.impl;

import app.dto.*;
import app.model.medication.Medication;
import app.model.medication.MedicationOffer;
import app.model.medication.MedicationQuantity;

import app.dto.MedicationOfferAndOrderDTO;
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

    @GetMapping(value = "/getSuppliersMedicationList/{supplierId}")
    public ResponseEntity<Collection<MedicationQuantity>> getSuppliersMedicationList(@PathVariable Long supplierId) {
        return new ResponseEntity<>(supplierService.getSuppliersMedicationList(supplierId), HttpStatus.OK);
    }

    @GetMapping(value = "/getNonMedicationsBySupplier/{supplierId}")
    public ResponseEntity<Collection<Medication>> getNonMedicationsBySupplier(@PathVariable Long supplierId) {
        return new ResponseEntity<>(supplierService.getNonMedicationsBySupplier(supplierId), HttpStatus.OK);
    }


    @PutMapping(value = "/addNewMedication", consumes = "application/json")
    public ResponseEntity<Boolean> addNewMedication(@RequestBody MedicationSupplierDTO   medicationSupplierDTO) {
        if(!supplierService.existsById(medicationSupplierDTO.getSupplierId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (supplierService.addNewMedication(medicationSupplierDTO))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PostMapping(consumes = "application/json", value = "/edit")
    public ResponseEntity<Boolean> editSuppliersMedicationQuantity(@RequestBody MedicationSupplierDTO medicationSupplierDTO){
        return new ResponseEntity<>(supplierService.editSuppliersMedicationQuantity(medicationSupplierDTO), HttpStatus.OK);
    }

    @PutMapping(value = "/deleteMedicationQuantity", consumes = "application/json")
    public ResponseEntity<Boolean> deleteMedicationQuantity(@RequestBody MedicationSupplierDTO   medicationSupplierDTO) {
        return new ResponseEntity<>(supplierService.deleteMedicationQuantity(medicationSupplierDTO), HttpStatus.OK);
    }
    @GetMapping(value = "/isAccountApproved/{id}")
    public ResponseEntity<Boolean> isAccountApproved(@PathVariable Long id){
        return new ResponseEntity<>(supplierService.read(id).get().getApprovedAccount(), HttpStatus.OK);
    }

    @PutMapping(value = "/pass")
    public ResponseEntity<Void> changePassword(@RequestBody UserPasswordDTO passwordKit) {
        try {
            supplierService.changePassword(passwordKit);
        }
        catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
