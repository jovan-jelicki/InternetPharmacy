package app.service;

import app.dto.LoginDTO;
import app.dto.LoginReturnDTO;
import app.model.user.*;
import app.repository.*;
import app.service.impl.*;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
    PatientRepository patientRepository;
    @Mock
    SupplierRepository supplierRepository;
    @Mock
    PharmacistRepository pharmacistRepository;
    @Mock
    SystemAdminRepository systemAdminRepository;
    @Mock
    PharmacyAdminRepository pharmacyAdminRepository;
    @Mock
    DermatologistRepository dermatologistRepository;

    @InjectMocks
    PatientServiceImpl patientService;
    @InjectMocks
    PharmacistServiceImpl pharmacistService;
    @InjectMocks
    PharmacyAdminServiceImpl pharmacyAdminService;
    @InjectMocks
    DermatologistServiceImpl dermatologistService;
    @InjectMocks
    SystemAdminServiceImpl systemAdminService;
    @InjectMocks
    SupplierServiceImpl supplierService;
    @InjectMocks
    FilterUserDetailsService filterUserDetailsService;

    @Test
    public void testUserLogin() {

        Credentials credentials = new Credentials();
        credentials.setEmail("maremare@gmail.com");
        credentials.setPassword("blabla");

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(credentials.getEmail());
        loginDTO.setPassword(credentials.getPassword());

        Credentials dermatologistCredentials = new Credentials();
        dermatologistCredentials.setEmail("anamaric@gmail.com");
        dermatologistCredentials.setPassword("anita");

        Credentials pharmacistCredentials = new Credentials();
        pharmacistCredentials.setEmail("koepkesk@gmail.com");
        pharmacistCredentials.setPassword("fefefre");

        Credentials systemAdminCredentials = new Credentials();
        systemAdminCredentials.setEmail("dsdsdsdsk@gmail.com");
        systemAdminCredentials.setPassword("fefefre");

        Credentials supplierCredentials = new Credentials();
        supplierCredentials.setEmail("errew@gmail.com");
        supplierCredentials.setPassword("fefefre");

        Credentials pharmacyAdminCredentials = new Credentials();
        pharmacyAdminCredentials.setEmail("ssssssss@gmail.com");
        pharmacyAdminCredentials.setPassword("fefefre");

        Patient patient = new Patient();
        patient.setId(0L);
        patient.setFirstName("Marko");
        patient.setLastName("Markovic");
        patient.setCredentials(credentials);

        Dermatologist dermatologist = new Dermatologist();
        dermatologist.setId(0L);
        dermatologist.setFirstName("Jovana");
        dermatologist.setLastName("Anic");
        dermatologist.setCredentials(dermatologistCredentials);

        Pharmacist pharmacist = new Pharmacist();
        pharmacist.setId(0L);
        pharmacist.setFirstName("Marija");
        pharmacist.setLastName("Petrovic");
        pharmacist.setCredentials(pharmacistCredentials);

        SystemAdmin systemAdmin = new SystemAdmin();
        systemAdmin.setId(0L);
        systemAdmin.setFirstName("Jelena");
        systemAdmin.setLastName("Zoric");
        systemAdmin.setCredentials(systemAdminCredentials);

        Supplier supplier = new Supplier();
        supplier.setId(0L);
        supplier.setFirstName("Pera");
        supplier.setLastName("Peric");
        supplier.setCredentials(supplierCredentials);

        PharmacyAdmin pharmacyAdmin = new PharmacyAdmin();
        pharmacyAdmin.setId(0L);
        pharmacyAdmin.setFirstName("Marko");
        pharmacyAdmin.setLastName("Petric");
        supplier.setCredentials(pharmacyAdminCredentials);

        LoginReturnDTO loginReturnDTO = new LoginReturnDTO();
        loginReturnDTO.setType(UserType.ROLE_patient);
        loginReturnDTO.setId(0L);
        loginReturnDTO.setEmail("maremare@gmail.com");

        when(patientRepository.findByEmailAndPassword(patient.getCredentials().getEmail(), patient.getCredentials().getPassword())).thenReturn(patient);
        when(dermatologistRepository.findByEmailAndPassword(dermatologist.getCredentials().getEmail(), dermatologist.getCredentials().getPassword())).thenReturn(dermatologist);
        when(pharmacistRepository.findByEmailAndPassword(pharmacist.getCredentials().getEmail(), pharmacist.getCredentials().getPassword())).thenReturn(pharmacist);
        when(systemAdminRepository.findByEmailAndPassword(systemAdmin.getCredentials().getEmail(), systemAdmin.getCredentials().getPassword())).thenReturn(systemAdmin);
        when(supplierRepository.findByEmailAndPassword(supplier.getCredentials().getEmail(), supplier.getCredentials().getPassword())).thenReturn(supplier);
        //   when(pharmacyAdminRepository.findByEmailAndPassword(pharmacyAdmin.getCredentials().getEmail(), pharmacyAdmin.getCredentials().getPassword())).thenReturn(pharmacyAdmin);

        assertThat(filterUserDetailsService.getUserForLogIn(loginDTO), is(equalTo(loginReturnDTO)));

    }
}
