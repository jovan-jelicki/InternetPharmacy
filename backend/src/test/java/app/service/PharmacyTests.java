package app.service;

import app.dto.AddMedicationToPharmacyDTO;
import app.model.medication.Medication;
import app.model.medication.MedicationQuantity;
import app.model.pharmacy.Pharmacy;
import app.repository.AppointmentRepository;
import app.repository.MedicationRepository;
import app.repository.PharmacyRepository;
import app.service.impl.MedicationServiceImpl;
import app.service.impl.PharmacyServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


//Created by David
@RunWith(SpringRunner.class)
@SpringBootTest
public class PharmacyTests {

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private PharmacyRepository pharmacyRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private MedicationServiceImpl medicationService;

    @InjectMocks
    private PharmacyServiceImpl pharmacyService;


    @Test
    @Transactional
    public void testAddNewMedicationToPharmacy(){
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(1L);
        pharmacy.setName("BioTech");

        Medication medication = new Medication();
        medication.setId(1L);

        Medication medication2 = new Medication();
        medication2.setId(2L);

        Medication medication3 = new Medication();
        medication3.setId(3L);

        MedicationQuantity medicationQuantityPharmacy1 = new MedicationQuantity();
        medicationQuantityPharmacy1.setMedication(medication2);
        medicationQuantityPharmacy1.setQuantity(35);

        MedicationQuantity medicationQuantityPharmacy2 = new MedicationQuantity();
        medicationQuantityPharmacy2.setMedication(medication3);
        medicationQuantityPharmacy2.setQuantity(79);

        ArrayList<MedicationQuantity> medicationQuantities = new ArrayList<>();
        medicationQuantities.add(medicationQuantityPharmacy1);
        medicationQuantities.add(medicationQuantityPharmacy2);

        pharmacy.setMedicationQuantity(medicationQuantities);

        AddMedicationToPharmacyDTO dto = new AddMedicationToPharmacyDTO();
        dto.setPharmacyId(1L);
        dto.setMedicationId(1L);
        dto.setQuantity(400);
        dto.setPriceDateStart(LocalDateTime.now());
        dto.setPriceDateEnd(LocalDateTime.now().plusDays(14));
        dto.setCost(99);

        when(pharmacyService.read(dto.getPharmacyId())).thenReturn(java.util.Optional.of(pharmacy));
        when(medicationService.read(dto.getMedicationId())).thenReturn(java.util.Optional.of(medication));
        when(pharmacyService.save(pharmacy)).thenReturn(pharmacy);
        assertThat(addNewMedication(dto), is(equalTo(true)));

    }

    private Boolean addNewMedication(AddMedicationToPharmacyDTO addMedicationToPharmacyDTO) {

        Pharmacy pharmacy = pharmacyService.read(addMedicationToPharmacyDTO.getPharmacyId()).get();

        Medication medication = medicationService.read(addMedicationToPharmacyDTO.getMedicationId()).get();

        //checks if pharmacy already has that medication
        if (pharmacy.getMedicationQuantity().stream().filter(medicationQuantity -> medicationQuantity.getMedication().getId().equals(medication.getId()))
                .collect(Collectors.toList()).size() != 0)
            return false;

        pharmacy.getMedicationQuantity().add(new MedicationQuantity(medication, addMedicationToPharmacyDTO.getQuantity()));


        return pharmacyService.save(pharmacy) != null;
    }
}
