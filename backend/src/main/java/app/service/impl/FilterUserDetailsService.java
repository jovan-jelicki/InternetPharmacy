package app.service.impl;

import app.dto.LoginDTO;
import app.dto.LoginReturnDTO;
import app.model.user.*;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FilterUserDetailsService implements UserDetailsService {

    @Autowired
    private PatientService patientService;
    @Autowired
    private PharmacistService pharmacistService;
    @Autowired
    private PharmacyAdminService pharmacyAdminService;
    @Autowired
    private DermatologistService dermatologistService;
    @Autowired
    private SystemAdminService systemAdminService;
    @Autowired
    private SupplierService supplierService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Patient patient = patientService.findByEmail(s);
        if (patient != null)
            return patient;
        Dermatologist dermatologist = dermatologistService.findByEmail(s);
        if (dermatologist != null)
            return dermatologist;
        Pharmacist pharmacist = pharmacistService.findByEmail(s);
        if (pharmacist != null)
            return pharmacist;
        PharmacyAdmin pharmacyAdmin = pharmacyAdminService.findByEmail(s);
        if (pharmacyAdmin != null)
            return pharmacyAdmin;
        Supplier supplier = supplierService.findByEmail(s);
        if (supplier != null)
            return supplier;
        SystemAdmin systemAdmin = systemAdminService.findByEmail(s);
        if (systemAdmin != null)
            return systemAdmin;
        throw new UsernameNotFoundException(String.format("No user found with username '%s'.", s));
    }

    public LoginReturnDTO getUserForLogIn(LoginDTO loginDTO) {

        LoginReturnDTO loginReturnDTO = new LoginReturnDTO();
        Patient patient = this.patientService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (patient != null) {
            loginReturnDTO.setId(patient.getId());
            loginReturnDTO.setType(patient.getUserType());
            loginReturnDTO.setEmail(patient.getCredentials().getEmail());
            return loginReturnDTO;
        }
        Dermatologist dermatologist = this.dermatologistService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (dermatologist != null) {
            loginReturnDTO.setId(dermatologist.getId());
            loginReturnDTO.setType(dermatologist.getUserType());
            loginReturnDTO.setEmail(dermatologist.getCredentials().getEmail());
            return loginReturnDTO;
        }
        Pharmacist pharmacist = this.pharmacistService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (pharmacist != null) {
            loginReturnDTO.setId(pharmacist.getId());
            loginReturnDTO.setType(pharmacist.getUserType());
            loginReturnDTO.setEmail(pharmacist.getCredentials().getEmail());
            return loginReturnDTO;
        }

        SystemAdmin systemAdmin = this.systemAdminService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (systemAdmin != null) {
            loginReturnDTO.setId(systemAdmin.getId());
            loginReturnDTO.setType(systemAdmin.getUserType());
            loginReturnDTO.setEmail(systemAdmin.getCredentials().getEmail());
            return loginReturnDTO;
        }

        PharmacyAdmin pharmacyAdmin = this.pharmacyAdminService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (pharmacyAdmin != null) {
            loginReturnDTO.setId(pharmacyAdmin.getId());
            loginReturnDTO.setType(pharmacyAdmin.getUserType());
            loginReturnDTO.setEmail(pharmacyAdmin.getCredentials().getEmail());
            return loginReturnDTO;
        }

        Supplier supplier = this.supplierService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (supplier != null) {
            loginReturnDTO.setId(supplier.getId());
            loginReturnDTO.setType(supplier.getUserType());
            loginReturnDTO.setEmail(supplier.getCredentials().getEmail());
            return loginReturnDTO;
        }
        return null;
    }
}
