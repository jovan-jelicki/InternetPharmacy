package app.service;

import app.dto.MedicationPriceListDTO;
import app.model.medication.Medication;
import app.model.medication.MedicationPriceList;
import app.model.medication.MedicationQuantity;
import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.repository.MedicationPriceListRepository;
import app.repository.PharmacyRepository;
import app.service.impl.MedicationPriceListServiceImpl;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicationPriceListServiceTests {

    @Mock
    private MedicationPriceListRepository medicationPriceListRepository;

    @Mock
    private PharmacyRepository pharmacyRepository;

    @InjectMocks
    private PharmacyServiceImpl pharmacyService;

    @Mock
    private MedicationPriceList medicationPriceList;



    @InjectMocks
    private MedicationPriceListServiceImpl medicationPriceListService;


    private void fillData() {
        Medication medication = new Medication();
        medication.setId(1L);

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(1L);

        MedicationQuantity medicationQuantity = new MedicationQuantity();
        medicationQuantity.setMedication(medication);
        medicationQuantity.setQuantity(300);
        ArrayList<MedicationQuantity> medicationQuantityArrayList = new ArrayList<>();
        medicationQuantityArrayList.add(medicationQuantity);
        pharmacy.setMedicationQuantity(medicationQuantityArrayList);

        Period period = new Period(LocalDateTime.of(2021,2,14,12,0), LocalDateTime.of(2021,2,27,13,0) );

        medicationPriceList.setMedication(medication);
        medicationPriceList.setPeriod(period);
        medicationPriceList.setPharmacy(pharmacy);
    }

    @Test
    @Transactional
    public void testSave(){
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(1L);
        Medication medication = new Medication();
        medication.setId(1L);

        MedicationQuantity medicationQuantity = new MedicationQuantity();
        medicationQuantity.setMedication(medication);
        medicationQuantity.setQuantity(300);
        ArrayList<MedicationQuantity> medicationQuantityArrayList = new ArrayList<>();
        medicationQuantityArrayList.add(medicationQuantity);
        pharmacy.setMedicationQuantity(medicationQuantityArrayList);
        pharmacy.setName("Temp");

        when(pharmacyService.save(pharmacy)).thenReturn(pharmacy);

        MedicationPriceList medicationPriceList = new MedicationPriceList();
        Period period = new Period(LocalDateTime.of(2021,2,14,12,0), LocalDateTime.of(2021,2,27,13,0) );

        medicationPriceList.setMedication(medication);
        medicationPriceList.setPeriod(period);
        medicationPriceList.setPharmacy(pharmacy);

        MedicationPriceListDTO medicationPriceListDTO = new MedicationPriceListDTO();
        medicationPriceListDTO.setMedicationId(medication.getId());
        medicationPriceListDTO.setPeriod(period);
        medicationPriceListDTO.setPharmacyId(pharmacy.getId());
        medicationPriceListDTO.setCost(233);

        when(pharmacyService.read(medicationPriceListDTO.getPharmacyId())).thenReturn(java.util.Optional.of(pharmacy));


        assertThat(createNewPriceList(medicationPriceListDTO), is(equalTo(true)));
    }

    private boolean isOverlapping (Period periodA, Period periodB) {
        if (periodA.getPeriodStart().isBefore(periodB.getPeriodStart()) && periodA.getPeriodStart().isBefore(periodB.getPeriodEnd())
                && periodA.getPeriodEnd().isBefore(periodB.getPeriodStart()) && periodA.getPeriodEnd().isBefore(periodB.getPeriodEnd()))
            return false;
        else return !periodB.getPeriodStart().isBefore(periodA.getPeriodStart()) || !periodB.getPeriodStart().isBefore(periodA.getPeriodEnd())
                || !periodB.getPeriodEnd().isBefore(periodA.getPeriodStart()) || !periodB.getPeriodEnd().isBefore(periodA.getPeriodEnd());
    }

    private Boolean createNewPriceList(MedicationPriceListDTO medicationPriceListDTO) {
        Pharmacy pharmacy = pharmacyService.read(medicationPriceListDTO.getPharmacyId()).get();
        Medication medication = pharmacy.getMedicationQuantity().stream().filter(medicationQuantity -> medicationQuantity.getMedication().getId()==medicationPriceListDTO.getMedicationId()).findFirst().get().getMedication();
        MedicationPriceList medicationPriceList = new MedicationPriceList(medication, medicationPriceListDTO.getCost(), medicationPriceListDTO.getPeriod(), pharmacy);


        if (medicationPriceListDTO.getPeriod().getPeriodStart().isAfter(medicationPriceListDTO.getPeriod().getPeriodEnd()))
            return false;

        for (MedicationPriceList medicationPriceListFor : medicationPriceListRepository.getMedicationPriceListHistoryByPharmacy(medicationPriceListDTO.getPharmacyId(), medicationPriceListDTO.getMedicationId()))
            if (isOverlapping(medicationPriceListDTO.getPeriod(), medicationPriceListFor.getPeriod()))
                return false;

        when(medicationPriceListService.save(medicationPriceList)).thenReturn(medicationPriceList);

        return medicationPriceListService.save(medicationPriceList) != null;
    }
}
