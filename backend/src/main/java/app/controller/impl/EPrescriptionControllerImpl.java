package app.controller.impl;

import app.dto.EPrescriptionSimpleInfoDTO;
import app.dto.MakeEPrescriptionDTO;
import app.service.EPrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
