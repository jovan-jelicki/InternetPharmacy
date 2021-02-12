package app.controller.impl;

import app.controller.MedicationReservationController;
import app.dto.GetMedicationReservationDTO;
import app.dto.MakeMedicationReservationDTO;
import app.dto.MedicationReservationSimpleInfoDTO;
import app.model.medication.MedicationReservation;
import app.model.medication.MedicationReservationStatus;
import app.service.MedicationReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/medicationReservation")
public class  MedicationReservationControllerImpl implements MedicationReservationController {
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
    @PreAuthorize("hasRole('pharmacist')")
    @PostMapping(value = "/getMedicationReservation")
    public ResponseEntity<MedicationReservationSimpleInfoDTO> getMedicationReservationFromPharmacy(@RequestBody GetMedicationReservationDTO getMedicationReservationDTO){
        MedicationReservation medicationReservation = medicationReservationService.getMedicationReservationFromPharmacy(getMedicationReservationDTO);
        if(medicationReservation == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        MedicationReservationSimpleInfoDTO medicationReservationSimpleInfoDTO = new MedicationReservationSimpleInfoDTO(medicationReservation);
        return new ResponseEntity<>(medicationReservationSimpleInfoDTO, HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasRole('pharmacist')")
    @PutMapping(value = "/giveMedicine/{id}")
    public ResponseEntity<Void> giveMedicine(@PathVariable Long id){
        MedicationReservation medicationReservation = medicationReservationService.read(id).get();
        if(medicationReservation == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        medicationReservation.setStatus(MedicationReservationStatus.successful);
        new Thread(new Runnable() {
            public void run(){
                medicationReservationService.sendEmailToPatient(medicationReservation.getPatient());
            }
        }).start();

        medicationReservationService.save(medicationReservation);
        return new ResponseEntity<>(HttpStatus.OK);
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

    @PutMapping(value = "/reserve", consumes = "application/json")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Long> reserve(@RequestBody MakeMedicationReservationDTO entity) {
        try {
            MedicationReservation medicationReservation = medicationReservationService.reserve(entity);
            if(medicationReservation != null)
                return new ResponseEntity<>(medicationReservation.getId(), HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/patient/{id}")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Collection<MedicationReservationSimpleInfoDTO>> findAllByPatientId(@PathVariable Long id) {
        return new ResponseEntity<>(
                medicationReservationService.findAllByPatientId(id)
                .stream()
                .map(MedicationReservationSimpleInfoDTO::new)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping(value = "/cancel/{id}")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        if(medicationReservationService.cancel(id))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
