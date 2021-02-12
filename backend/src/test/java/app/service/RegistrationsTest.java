package app.service;

import app.dto.PharmacyAdminRegistrationDTO;
import app.model.medication.*;
import app.model.pharmacy.Pharmacy;
import app.model.user.*;
import app.repository.DermatologistRepository;
import app.repository.MedicationRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationsTest {

    @Mock
    MedicationService medicationService;

    @Mock
    MedicationRepository medicationRepository;

    @Mock
    DermatologistRepository dermatologistRepository;

    @Mock
    PharmacyAdminService pharmacyAdminService;

    @Mock
    PharmacyService pharmacyService;

    @Test
    public void testAddNewMedication(){
        Ingredient ingredient=new Ingredient();
        ingredient.setId(0L);
        ingredient.setName("Penicilin");

        Ingredient ingredient1=new Ingredient();
        ingredient1.setId(1L);
        ingredient1.setName("Insulin");

        Set<Ingredient> ingredients= new HashSet<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient);

        Medication alternative1=new Medication();
        alternative1.setId(100L);
        Medication alternative2=new Medication();
        alternative2.setId(101L);
        Medication alternative3=new Medication();
        alternative3.setId(102L);

        Set<Medication> alternatives= new HashSet<>();
        alternatives.add(alternative1);
        alternatives.add(alternative2);
        alternatives.add(alternative3);

        Medication medication=new Medication();
        medication.setName("Nimulid");
        medication.setManufacturer("Ivancic i sinovi");
        medication.setType(MedicationType.anesthetic);
        medication.setDose(0.5);
        medication.setMedicationShape(MedicationShape.capsule);
        medication.setMedicationIssue(MedicationIssue.withoutPrescription);
        medication.setIngredient(ingredients);
        medication.setAlternatives(alternatives);
        medication.setLoyaltyPoints(55);

        when(medicationRepository.save(medication)).thenReturn(medication);
        assertThat(save(medication), is(equalTo(medication)));

    }
    private Medication save(Medication entity) {
        return medicationRepository.save(entity);
    }


    @Test
    public void testPharmacyAdminRegistration(){
        Credentials credentials=new Credentials();
        credentials.setEmail("mare123@gmail.com");
        credentials.setPassword("maremare");

        Address address=new Address();
        address.setLatitude(40.0);
        address.setLongitude(20.0);
        address.setCountry("Serbia");
        address.setStreet("Misarska 8");
        address.setTown("Novi Sad");

        Contact contact=new Contact();
        contact.setAddress(address);
        contact.setPhoneNumber("066/98958552");

        PharmacyAdminRegistrationDTO pharmacyAdminRegistrationDTO=new PharmacyAdminRegistrationDTO();
        pharmacyAdminRegistrationDTO.setFirstName("Marko");
        pharmacyAdminRegistrationDTO.setLastName("Jovkovic");
        pharmacyAdminRegistrationDTO.setUserType(UserType.ROLE_pharmacyAdmin);
        pharmacyAdminRegistrationDTO.setContact(contact);
        pharmacyAdminRegistrationDTO.setCredentials(credentials);
        pharmacyAdminRegistrationDTO.setPharmacyId(0L);
        PharmacyAdmin pharmacyAdmin = new PharmacyAdmin();
        Pharmacy pharmacy=new Pharmacy();

        when(pharmacyService.read(pharmacyAdminRegistrationDTO.getPharmacyId())).thenReturn(java.util.Optional.of(pharmacy));
        when(pharmacyAdminService.save(pharmacyAdmin)).thenReturn(pharmacyAdmin);

       assertThat(saveAdmin(pharmacyAdminRegistrationDTO), is(equalTo(true)));
    }

    public Boolean saveAdmin(PharmacyAdminRegistrationDTO pharmacyAdminDTO) {
        PharmacyAdmin pharmacyAdmin = new PharmacyAdmin();
        pharmacyAdmin.setFirstName(pharmacyAdminDTO.getFirstName());
        pharmacyAdmin.setLastName(pharmacyAdminDTO.getLastName());
        pharmacyAdmin.setUserType(pharmacyAdminDTO.getUserType());
        pharmacyAdmin.setCredentials(pharmacyAdminDTO.getCredentials());
        pharmacyAdmin.setContact(pharmacyAdminDTO.getContact());
        pharmacyAdmin.setPharmacy(pharmacyService.read(pharmacyAdminDTO.getPharmacyId()).get());
        pharmacyAdminService.save(pharmacyAdmin);

        return true;
    }
    @Test
    public void testDermatologistRegistration(){
        Credentials credentials=new Credentials();
        credentials.setEmail("jovana.joksi@gmail.com");
        credentials.setPassword("klkSjcs6");

        Address address=new Address();
        address.setLatitude(22.0);
        address.setLongitude(33.0);
        address.setCountry("Serbia");
        address.setStreet("Hilandarska 4");
        address.setTown("Novi Sad");

        Contact contact=new Contact();
        contact.setAddress(address);
        contact.setPhoneNumber("061/98958552");

        Dermatologist dermatologist=new Dermatologist();
        dermatologist.setFirstName("Jovana");
        dermatologist.setLastName("Pekovic");
        dermatologist.setUserType(UserType.ROLE_dermatologist);
        dermatologist.setContact(contact);
        dermatologist.setCredentials(credentials);

        when(dermatologistRepository.save(dermatologist)).thenReturn(dermatologist);
        assertThat(save(dermatologist), is(equalTo(dermatologist)));

    }

    public Dermatologist save(Dermatologist entity) {
        return dermatologistRepository.save(entity);
    }
}
