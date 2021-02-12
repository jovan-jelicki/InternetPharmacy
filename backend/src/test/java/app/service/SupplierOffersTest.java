package app.service;

import app.dto.MedicationOfferAndOrderDTO;
import app.model.medication.*;
import app.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupplierOffersTest {

    @Mock
    private MedicationOfferService medicationOfferService;

    @Test
    public void editMedicationOffer(){
        MedicationQuantity medicationQuantity1=new MedicationQuantity();
        Medication medication1=new Medication();
        medication1.setId(1L);
        medicationQuantity1.setId(1L);
        medicationQuantity1.setMedication(medication1);
        medicationQuantity1.setQuantity(55);

        MedicationQuantity medicationQuantity2=new MedicationQuantity();
        Medication medication2=new Medication();
        medication2.setId(2L);
        medicationQuantity2.setId(2L);
        medicationQuantity2.setMedication(medication2);
        medicationQuantity2.setQuantity(14);

        ArrayList<MedicationQuantity> medicationQuantities = new ArrayList<>();
        medicationQuantities.add(medicationQuantity1);
        medicationQuantities.add(medicationQuantity2);


        MedicationOrder medicationOrder= new MedicationOrder();
        medicationOrder.setId(0L);
        medicationOrder.setMedicationQuantity(medicationQuantities);


        MedicationOffer medicationOffer=new MedicationOffer();
        medicationOffer.setId(0L);
        medicationOffer.setCost(555);
        medicationOffer.setShippingDate(LocalDateTime.of(2021,3,20, 11,0,0,0));
        medicationOffer.setMedicationOrder(medicationOrder);


        MedicationOfferAndOrderDTO medicationOfferAndOrderDTO=new MedicationOfferAndOrderDTO();
        medicationOfferAndOrderDTO.setOfferId(medicationOffer.getId());
        medicationOfferAndOrderDTO.setOrderId(medicationOrder.getId());
        medicationOfferAndOrderDTO.setShippingDate(LocalDateTime.of(2021,5,23, 12,0,0,0));
        medicationOfferAndOrderDTO.setCost(200);

        when(medicationOfferService.read(medicationOfferAndOrderDTO.getOfferId())).thenReturn(java.util.Optional.of(medicationOffer));
        when(medicationOfferService.save(medicationOffer)).thenReturn(medicationOffer);

        assertThat(editMedicationOffer(medicationOfferAndOrderDTO), is(equalTo(true)));

    }

    private Boolean editMedicationOffer(MedicationOfferAndOrderDTO medicationOfferAndOrderDTO) {
        MedicationOffer medOffer=medicationOfferService.read(medicationOfferAndOrderDTO.getOfferId()).get();
        medOffer.setCost(medicationOfferAndOrderDTO.getCost());
        medOffer.setShippingDate(medicationOfferAndOrderDTO.getShippingDate());
        medicationOfferService.save(medOffer);

        return medOffer!=null;
    }
}
