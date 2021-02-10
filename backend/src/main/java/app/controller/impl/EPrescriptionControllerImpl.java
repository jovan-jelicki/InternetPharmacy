package app.controller.impl;

import app.dto.*;
import app.service.EPrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/eprescriptions")
public class EPrescriptionControllerImpl {
    private final EPrescriptionService ePrescriptionService;

    @Autowired
    public EPrescriptionControllerImpl(EPrescriptionService ePrescriptionService) {
        this.ePrescriptionService = ePrescriptionService;
    }

    @PreAuthorize("hasAnyRole('dermatologist, pharmacist')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<EPrescriptionSimpleInfoDTO> createEPrescription(@RequestBody MakeEPrescriptionDTO makeEPrescriptionDTO) {
        EPrescriptionSimpleInfoDTO ePrescriptionSimpleInfoDTO = ePrescriptionService.reserveEPrescription(makeEPrescriptionDTO);
        if (ePrescriptionSimpleInfoDTO == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ePrescriptionSimpleInfoDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/patient/{id}")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Collection<EPrescriptionSimpleInfoDTO>> findAllByPatientId(@PathVariable Long id) {
        Collection<EPrescriptionSimpleInfoDTO> prescriptions = ePrescriptionService.findAllByPatientId(id)
                .stream()
                .map(EPrescriptionSimpleInfoDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('patient')")
    @GetMapping(value = "/getPharmacyForQR/{ePrescriptionId}")
    public ResponseEntity<Collection<PharmacyQRDTO>> getPharmacyForQR(@PathVariable Long ePrescriptionId) {
        Collection<PharmacyQRDTO> pharmacyQRDTOS=ePrescriptionService.getPharmacyForQR(ePrescriptionId);
        return new ResponseEntity(pharmacyQRDTOS, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('patient')")
    @PostMapping(value = "/buyMedication")
    public ResponseEntity<Boolean> buyMedication(@RequestBody PharmacyPrescriptionDTO pharmacyPrescriptionDTO) {
        if(!ePrescriptionService.existsById(pharmacyPrescriptionDTO.getPrescriptionId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (ePrescriptionService.buyMedication(pharmacyPrescriptionDTO.getPharmacyId(),pharmacyPrescriptionDTO.getPrescriptionId()))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
