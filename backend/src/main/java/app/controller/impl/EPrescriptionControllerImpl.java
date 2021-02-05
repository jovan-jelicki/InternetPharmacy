package app.controller.impl;

import app.dto.EPrescriptionSimpleInfoDTO;
import app.dto.MakeEPrescriptionDTO;
import app.model.medication.EPrescription;
import app.service.EPrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "api/eprescriptions")
public class EPrescriptionControllerImpl {
    private final EPrescriptionService ePrescriptionService;

    @Autowired
    public EPrescriptionControllerImpl(EPrescriptionService ePrescriptionService) {
        this.ePrescriptionService = ePrescriptionService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<EPrescriptionSimpleInfoDTO> createEPrescription(@RequestBody MakeEPrescriptionDTO makeEPrescriptionDTO){
        EPrescriptionSimpleInfoDTO ePrescriptionSimpleInfoDTO = ePrescriptionService.reserveEPrescription(makeEPrescriptionDTO);
        if(ePrescriptionSimpleInfoDTO == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ePrescriptionSimpleInfoDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/patient/{id}")
    public ResponseEntity<Collection<EPrescription>> findAllByPatientId(Long id) {
        return new ResponseEntity<>(ePrescriptionService.findAllByPatientId(id), HttpStatus.OK);
    }
}
