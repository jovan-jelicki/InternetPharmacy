package app.controller.impl;

import app.dto.MedicationOrderDTO;
import app.dto.MedicationOrderSupplierDTO;
import app.model.medication.MedicationOrder;
import app.service.MedicationOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping(value = "api/medicationOrder")
public class MedicationOrderController {
    private final MedicationOrderService medicationOrderService;

    @Autowired
    public MedicationOrderController(MedicationOrderService medicationOrderService) {
        this.medicationOrderService = medicationOrderService;
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<Collection<MedicationOrder>> read() {
        return new ResponseEntity<>(medicationOrderService.read(), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllActive")
    public ResponseEntity<Collection<MedicationOrderSupplierDTO>> getAllActive() {
        return new ResponseEntity<>(medicationOrderService.getAllActive(), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", value = "/newMedicationOrder")
    public ResponseEntity<Boolean> newMedicationOrder(@RequestBody MedicationOrderDTO medicationOrderDTO){
        if (medicationOrderService.createNewMedicationOrder(medicationOrderDTO))
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);    }

    @PostMapping(consumes = "application/json", value = "/editMedicationOrder")
    public ResponseEntity<Boolean> editMedicationOrder(@RequestBody MedicationOrderDTO medicationOrderDTO){
        if (medicationOrderService.editMedicationOrder(medicationOrderDTO))
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/getAllMedicationOrdersByPharmacy/{pharmacyId}")
    public ResponseEntity<Collection<MedicationOrderDTO>> newMedicationOrder(@PathVariable Long pharmacyId){
        return new ResponseEntity<>(medicationOrderService.getAllMedicationOrdersByPharmacy(pharmacyId), HttpStatus.OK);
    }

    @GetMapping(value = "/getOrderByPharmacyAdmin")
    public ResponseEntity<Collection<MedicationOrderDTO>> getMedicationOrderByPharmacyAdmin(@PathVariable Long pharmacyAdminId){
        return new ResponseEntity<>(medicationOrderService.getMedicationOrderByPharmacyAdmin(pharmacyAdminId), HttpStatus.OK);
    }

    @GetMapping(value = "/checkIfOrderIsEditable/{orderId}")
    public ResponseEntity<Boolean> checkIfOrderIsEditable(@PathVariable Long orderId){
        return new ResponseEntity<>(medicationOrderService.checkIfOrderIsEditable(orderId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteMedicationOrder/{orderId}")
    public ResponseEntity<Boolean> deleteMedicationOrder(@PathVariable Long orderId){
        if (medicationOrderService.deleteMedicationOrder(orderId))
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
}
