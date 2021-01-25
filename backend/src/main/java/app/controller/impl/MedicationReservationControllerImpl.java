package app.controller.impl;

import app.controller.MedicationReservationController;
import app.model.medication.MedicationReservation;
import app.service.MedicationReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/medicationReservation")
public class MedicationReservationControllerImpl implements MedicationReservationController {
    private final MedicationReservationService medicationReservationService;

    @Autowired
    public MedicationReservationControllerImpl(MedicationReservationService medicationReservationService) {
        this.medicationReservationService = medicationReservationService;
    }

    @Override
    @PostMapping(consumes = "application/json")
    public ResponseEntity<MedicationReservation> save(@RequestBody MedicationReservation entity) {
        return new ResponseEntity<>(medicationReservationService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(consumes = "application/json")
    public ResponseEntity<MedicationReservation> update(@RequestBody MedicationReservation entity) {
        if(!medicationReservationService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(medicationReservationService.save(entity), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<Collection<MedicationReservation>> read() {
        return new ResponseEntity<>(medicationReservationService.read(), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<MedicationReservation>> read(@PathVariable Long id) {
        return new ResponseEntity<>(medicationReservationService.read(id), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!medicationReservationService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        medicationReservationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
