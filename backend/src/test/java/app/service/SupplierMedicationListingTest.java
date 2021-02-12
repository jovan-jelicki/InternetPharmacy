package app.service;


import app.model.medication.Medication;
import app.model.medication.MedicationQuantity;
import app.model.user.Supplier;
import app.repository.SupplierRepository;
import app.service.impl.SupplierServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupplierMedicationListingTest {

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private Supplier supplier;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    @Test
    public void testChangeSupplierMedicationListing(){
        Supplier supplier = new Supplier();

        MedicationQuantity medicationQuantity=new MedicationQuantity();
        Medication medication=new Medication();
        medication.setName("Nimulid");
        medicationQuantity.setMedication(medication);
        medicationQuantity.setQuantity(55);

        //supplier.setMedicationQuantity(medicationQuantity);

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));


    }
}
