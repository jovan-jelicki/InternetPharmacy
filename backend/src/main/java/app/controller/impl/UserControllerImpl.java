package app.controller.impl;

import app.dto.LoginDTO;
import app.dto.LoginReturnDTO;
import app.model.user.*;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/users")
public class UserControllerImpl {
    private final PatientService patientService;
    private final PharmacistService pharmacistService;
    private final PharmacyAdminService pharmacyAdminService;
    private final DermatologistService dermatologistService;
    private final SystemAdminService systemAdminService;
    private final SupplierService supplierService;

    @Autowired
    public UserControllerImpl(PatientService patientService, PharmacyAdminService pharmacyAdminService, DermatologistService dermatologistService,
                              PharmacistService pharmacistService,SystemAdminService systemAdminService,SupplierService supplierService) {
        this.patientService = patientService;
        this.pharmacyAdminService = pharmacyAdminService;
        this.dermatologistService = dermatologistService;
        this.pharmacistService =  pharmacistService;
        this.systemAdminService=systemAdminService;
        this.supplierService=supplierService;
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<LoginReturnDTO> logIn(@RequestBody LoginDTO loginDTO) {
        LoginReturnDTO loginReturnDTO=new LoginReturnDTO();

        Patient patient = this.patientService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if(patient!=null) {
            loginReturnDTO.setId(patient.getId());
            loginReturnDTO.setType(patient.getUserType());
            return new ResponseEntity(loginReturnDTO, HttpStatus.OK);
        }
        Dermatologist dermatologist=this.dermatologistService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if(dermatologist!=null) {
            loginReturnDTO.setId(dermatologist.getId());
            loginReturnDTO.setType(dermatologist.getUserType());
            return new ResponseEntity(loginReturnDTO, HttpStatus.OK);
        }
        Pharmacist pharmacist=this.pharmacistService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if(pharmacist!=null) {
            loginReturnDTO.setId(pharmacist.getId());
            loginReturnDTO.setType(pharmacist.getUserType());
            return new ResponseEntity(loginReturnDTO, HttpStatus.OK);
        }

        SystemAdmin systemAdmin=this.systemAdminService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if(systemAdmin!=null) {
            loginReturnDTO.setId(systemAdmin.getId());
            loginReturnDTO.setType(systemAdmin.getUserType());
            return new ResponseEntity(loginReturnDTO, HttpStatus.OK);
        }

        PharmacyAdmin pharmacyAdmin=this.pharmacyAdminService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if(pharmacyAdmin!=null) {
            loginReturnDTO.setId(pharmacyAdmin.getId());
            loginReturnDTO.setType(pharmacyAdmin.getUserType());
            return new ResponseEntity(loginReturnDTO, HttpStatus.OK);
        }

        Supplier supplier=this.supplierService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if(supplier!=null) {
            loginReturnDTO.setId(supplier.getId());
            loginReturnDTO.setType(supplier.getUserType());
            return new ResponseEntity(loginReturnDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
