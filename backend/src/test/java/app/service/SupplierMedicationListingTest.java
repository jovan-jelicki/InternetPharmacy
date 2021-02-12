package app.service;


import app.dto.MedicationSupplierDTO;
import app.model.medication.Medication;
import app.model.medication.MedicationQuantity;
import app.model.user.Supplier;
import app.repository.SupplierRepository;
import app.service.impl.MedicationServiceImpl;
import app.service.impl.SupplierServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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

    @Mock
    private MedicationServiceImpl medicationService;


    private void fillData(MedicationSupplierDTO medicationSupplierDTO, Medication medication1){
        supplier.setId(1L);

        MedicationQuantity medicationQuantity1=new MedicationQuantity();
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


        supplier.setMedicationQuantity(medicationQuantities);

        medicationSupplierDTO.setMedicationId(1L);
        medicationSupplierDTO.setSupplierId(1L);
        medicationSupplierDTO.setQuantity(789);
        medicationSupplierDTO.setMedicationQuantityId(1L);
    }

    @Test
    public void testChangeSupplierMedicationListing(){
        MedicationSupplierDTO medicationSupplierDTO= new MedicationSupplierDTO();
        Medication medication1=new Medication();

        fillData(medicationSupplierDTO,medication1);

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(medicationService.read(medicationSupplierDTO.getMedicationId())).thenReturn(java.util.Optional.of(medication1));
        when(supplierService.save(supplier)).thenReturn(supplier);

        assertThat(editSuppliersMedicationQuantity(medicationSupplierDTO), is(equalTo(true)));
    }

    private Boolean editSuppliersMedicationQuantity(MedicationSupplierDTO medicationSupplierDTO) {
        Supplier supplier=supplierService.read(medicationSupplierDTO.getSupplierId()).get();

        for(MedicationQuantity medicationQuantity : supplier.getMedicationQuantity()){
            if(medicationQuantity.getId()==medicationSupplierDTO.getMedicationQuantityId()){
                medicationQuantity.setQuantity(medicationSupplierDTO.getQuantity());
            }
        }

        return  supplierService.save(supplier)!=null;
    }
}
