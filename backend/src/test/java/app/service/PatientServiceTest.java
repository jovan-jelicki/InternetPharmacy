package app.service;

import app.dto.PharmacyPlainDTO;
import app.model.pharmacy.Pharmacy;
import app.model.pharmacy.Promotion;
import app.model.user.Patient;
import app.repository.PatientRepository;
import app.service.impl.PatientServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    PatientServiceImpl patientService;

    @Test
    public void getPromotionPharmaciesTest() {
        Pharmacy pharmacy1 = new Pharmacy();
        pharmacy1.setName("Pfizer");
        pharmacy1.setId(1l);
        Pharmacy pharmacy2 = new Pharmacy();
        pharmacy2.setId(2l);
        pharmacy2.setName("Moderna");
        Promotion promotion1 = new Promotion();
        promotion1.setPharmacy(pharmacy1);
        Promotion promotion2 = new Promotion();
        promotion2.setPharmacy(pharmacy2);
        Patient patient = new Patient();
        ArrayList<Promotion> promotions = new ArrayList<>();
        promotions.add(promotion1);
        promotions.add(promotion2);
        patient.setPromotions(promotions);
        when(patientRepository.findById(0l)).thenReturn(Optional.of(patient));

        Collection<PharmacyPlainDTO> pharmacies = patientService.getPromotionPharmacies(0l);

        Assert.assertEquals(2, pharmacies.size());
        pharmacies.forEach(p -> Assert.assertTrue(p.getName().equals("Pfizer") || p.getName().equals("Moderna")));
    }
}
