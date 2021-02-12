package app.service;

import app.dto.LoginDTO;
import app.dto.LoginReturnDTO;
import app.model.user.*;
import app.service.impl.FilterUserDetailsService;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogInTest {

    @Mock
    FilterUserDetailsService filterUserDetailsService;
    @Mock
    PatientService patientService;
    @Mock
    PharmacistService pharmacistService;
    @Mock
    PharmacyAdminService pharmacyAdminService;
    @Mock
    DermatologistService dermatologistService;
    @Mock
    SystemAdminService systemAdminService;
    @Mock
    SupplierService supplierService;


    @Test
    public void testUserLogin(){

        Credentials credentials= new Credentials();
        credentials.setEmail("blabla@gmail.com");
        credentials.setPassword("blabla");

        LoginDTO loginDTO=new LoginDTO();
        loginDTO.setEmail(credentials.getEmail());
        loginDTO.setPassword(credentials.getPassword());

        Credentials dermatologistCredentials= new Credentials();
        dermatologistCredentials.setEmail("anamaric@gmail.com");
        dermatologistCredentials.setPassword("anita");

        Credentials pharmacistCredentials= new Credentials();
        pharmacistCredentials.setEmail("koepkesk@gmail.com");
        pharmacistCredentials.setPassword("fefefre");

        Credentials systemAdminCredentials= new Credentials();
        systemAdminCredentials.setEmail("dsdsdsdsk@gmail.com");
        systemAdminCredentials.setPassword("fefefre");

        Credentials supplierCredentials= new Credentials();
        supplierCredentials.setEmail("errew@gmail.com");
        supplierCredentials.setPassword("fefefre");

        Credentials pharmacyAdminCredentials= new Credentials();
        pharmacyAdminCredentials.setEmail("ssssssss@gmail.com");
        pharmacyAdminCredentials.setPassword("fefefre");

        Patient patient=new Patient();
        patient.setId(0L);
        patient.setFirstName("Marko");
        patient.setLastName("Markovic");
        patient.setCredentials(credentials);

        Dermatologist dermatologist=new Dermatologist();
        dermatologist.setId(0L);
        dermatologist.setFirstName("Jovana");
        dermatologist.setLastName("Anic");
        dermatologist.setCredentials(dermatologistCredentials);

        Pharmacist pharmacist= new Pharmacist();
        pharmacist.setId(0L);
        pharmacist.setFirstName("Marija");
        pharmacist.setLastName("Petrovic");
        pharmacist.setCredentials(pharmacistCredentials);

        SystemAdmin systemAdmin= new SystemAdmin();
        systemAdmin.setId(0L);
        systemAdmin.setFirstName("Jelena");
        systemAdmin.setLastName("Zoric");
        systemAdmin.setCredentials(systemAdminCredentials);

        Supplier supplier= new Supplier();
        supplier.setId(0L);
        supplier.setFirstName("Pera");
        supplier.setLastName("Peric");
        supplier.setCredentials(supplierCredentials);

        PharmacyAdmin pharmacyAdmin= new PharmacyAdmin();
        pharmacyAdmin.setId(0L);
        pharmacyAdmin.setFirstName("Marko");
        pharmacyAdmin.setLastName("Petric");
        supplier.setCredentials(pharmacyAdminCredentials);

        LoginReturnDTO loginReturnDTO=new LoginReturnDTO();
        loginReturnDTO.setType(UserType.ROLE_patient);
        loginReturnDTO.setId(0L);
        loginReturnDTO.setEmail("maremare@gmail.com");

        when(patientService.findByEmailAndPassword(patient.getCredentials().getEmail(), patient.getCredentials().getPassword())).thenReturn(patient);
        when(dermatologistService.findByEmailAndPassword(dermatologist.getCredentials().getEmail(), dermatologist.getCredentials().getPassword())).thenReturn(dermatologist);
        when(pharmacistService.findByEmailAndPassword(pharmacist.getCredentials().getEmail(), pharmacist.getCredentials().getPassword())).thenReturn(pharmacist);
        when(systemAdminService.findByEmailAndPassword(systemAdmin.getCredentials().getEmail(), systemAdmin.getCredentials().getPassword())).thenReturn(systemAdmin);
        when(supplierService.findByEmailAndPassword(supplier.getCredentials().getEmail(), supplier.getCredentials().getPassword())).thenReturn(supplier);
        when(pharmacyAdminService.findByEmailAndPassword(pharmacyAdmin.getCredentials().getEmail(), pharmacyAdmin.getCredentials().getPassword())).thenReturn(pharmacyAdmin);

        assertThat(getUserForLogIn(loginDTO), is(equalTo(null)));
    }

    private LoginReturnDTO getUserForLogIn(LoginDTO loginDTO) {

        LoginReturnDTO loginReturnDTO = new LoginReturnDTO();
        Patient patient = this.patientService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (patient != null) {
            loginReturnDTO.setId(patient.getId());
            loginReturnDTO.setType(UserType.ROLE_patient);
            loginReturnDTO.setEmail(patient.getCredentials().getEmail());
            System.out.println("pacijent");
            return loginReturnDTO;
        }
        Dermatologist dermatologist = this.dermatologistService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (dermatologist != null) {
            loginReturnDTO.setId(dermatologist.getId());
            loginReturnDTO.setType(dermatologist.getUserType());
            loginReturnDTO.setEmail(dermatologist.getCredentials().getEmail());
            System.out.println("dermatolog");

            return loginReturnDTO;
        }
        Pharmacist pharmacist = this.pharmacistService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (pharmacist != null) {
            loginReturnDTO.setId(pharmacist.getId());
            loginReturnDTO.setType(pharmacist.getUserType());
            loginReturnDTO.setEmail(pharmacist.getCredentials().getEmail());
            System.out.println("pharmacist");

            return loginReturnDTO;
        }

        SystemAdmin systemAdmin = this.systemAdminService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (systemAdmin != null) {
            loginReturnDTO.setId(systemAdmin.getId());
            loginReturnDTO.setType(systemAdmin.getUserType());
            loginReturnDTO.setEmail(systemAdmin.getCredentials().getEmail());
            System.out.println("systemAdmin");

            return loginReturnDTO;
        }

        PharmacyAdmin pharmacyAdmin = this.pharmacyAdminService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (pharmacyAdmin != null) {
            loginReturnDTO.setId(pharmacyAdmin.getId());
            loginReturnDTO.setType(pharmacyAdmin.getUserType());
            loginReturnDTO.setEmail(pharmacyAdmin.getCredentials().getEmail());
            System.out.println("pharmacyAdmin");

            return loginReturnDTO;
        }

        Supplier supplier = this.supplierService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (supplier != null) {
            loginReturnDTO.setId(supplier.getId());
            loginReturnDTO.setType(supplier.getUserType());
            loginReturnDTO.setEmail(supplier.getCredentials().getEmail());
            System.out.println("supplier");

            return loginReturnDTO;
        }
        return null;
    }
}
