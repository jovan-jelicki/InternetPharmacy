package app.controller.impl;

import app.dto.*;
import app.model.medication.Medication;
import app.model.medication.MedicationOffer;
import app.model.medication.MedicationQuantity;

import app.dto.MedicationOfferAndOrderDTO;
import app.model.user.Dermatologist;
import app.model.user.Supplier;
import app.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('supplier')")
    @GetMapping(value = "/getAllBySupplier/{supplierId}")
    public ResponseEntity<Collection<MedicationOfferAndOrderDTO>> getMedicationOffersBySupplier(@PathVariable Long supplierId) {
        return new ResponseEntity<>(supplierService.getMedicationOffersBySupplier(supplierId), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('supplier')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PharmacistDermatologistProfileDTO> read(@PathVariable Long id) {
        return new ResponseEntity<>(new PharmacistDermatologistProfileDTO(supplierService.read(id).get()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('supplier')")
    @PutMapping(consumes = "application/json")
    public ResponseEntity<PharmacistDermatologistProfileDTO> update(@RequestBody PharmacistDermatologistProfileDTO entity) {
        if(!supplierService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Supplier supplier = supplierService.read(entity.getId()).get();
        supplierService.save(entity.convertDtoToSupplier(supplier));
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('systemAdmin')")
    @PostMapping(value="/save", consumes = "application/json")
    public ResponseEntity<Supplier> save(@RequestBody Supplier entity) {
        return new ResponseEntity<>(supplierService.save(entity), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('supplier')")
    @GetMapping(value = "/getSuppliersMedicationList/{supplierId}")
    public ResponseEntity<Collection<MedicationQuantity>> getSuppliersMedicationList(@PathVariable Long supplierId) {
        return new ResponseEntity<>(supplierService.getSuppliersMedicationList(supplierId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('supplier')")
    @GetMapping(value = "/getNonMedicationsBySupplier/{supplierId}")
    public ResponseEntity<Collection<Medication>> getNonMedicationsBySupplier(@PathVariable Long supplierId) {
        return new ResponseEntity<>(supplierService.getNonMedicationsBySupplier(supplierId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('supplier')")
    @PutMapping(value = "/addNewMedication", consumes = "application/json")
    public ResponseEntity<Boolean> addNewMedication(@RequestBody MedicationSupplierDTO   medicationSupplierDTO) {
        if(!supplierService.existsById(medicationSupplierDTO.getSupplierId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (supplierService.addNewMedication(medicationSupplierDTO))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('supplier')")
    @PostMapping(consumes = "application/json", value = "/edit")
    public ResponseEntity<Boolean> editSuppliersMedicationQuantity(@RequestBody MedicationSupplierDTO medicationSupplierDTO){
        return new ResponseEntity<>(supplierService.editSuppliersMedicationQuantity(medicationSupplierDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('supplier')")
    @PutMapping(value = "/deleteMedicationQuantity", consumes = "application/json")
    public ResponseEntity<Boolean> deleteMedicationQuantity(@RequestBody MedicationSupplierDTO   medicationSupplierDTO) {
        return new ResponseEntity<>(supplierService.deleteMedicationQuantity(medicationSupplierDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('supplier')")
    @GetMapping(value = "/isAccountApproved/{id}")
    public ResponseEntity<Boolean> isAccountApproved(@PathVariable Long id){
        return new ResponseEntity<>(supplierService.read(id).get().getApprovedAccount(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('supplier')")
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
