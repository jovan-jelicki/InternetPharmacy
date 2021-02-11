package app.service;

import app.dto.MedicationOfferDTO;
import app.model.medication.*;
import app.model.pharmacy.Pharmacy;
import app.model.user.PharmacyAdmin;
import app.model.user.Supplier;
import app.repository.MedicationOfferRepository;
import app.repository.MedicationOrderRepository;
import app.repository.PharmacyRepository;
import app.repository.SupplierRepository;
import app.service.impl.MedicationOfferServiceImpl;
import app.service.impl.MedicationOrderServiceImpl;
import app.service.impl.PharmacyServiceImpl;
import app.service.impl.SupplierServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


//Created by David
@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicationOfferTests {

    @Mock
    private MedicationOrderRepository medicationOrderRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private PharmacyRepository pharmacyRepository;

    @Mock
    private MedicationOfferRepository medicationOfferRepository;

    @InjectMocks
    private PharmacyServiceImpl pharmacyService;

    @InjectMocks
    private SupplierServiceImpl supplierService;



    @InjectMocks
    private MedicationOfferServiceImpl medicationOfferService;

    @InjectMocks
    private MedicationOrderServiceImpl medicationOrderService;


    @Test
    @Transactional
    public void testSupplierQuantityUpdatedSuccessfully(){
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(1L);

        //define medication order
        MedicationOrder medicationOrder = new MedicationOrder();
        medicationOrder.setDeadline(LocalDateTime.now().plusHours(2));
        medicationOrder.setId(1L);
        medicationOrder.setActive(true);
        PharmacyAdmin pharmacyAdmin = new PharmacyAdmin();
        pharmacyAdmin.setId(1L);
        pharmacyAdmin.setPharmacy(pharmacy);
        medicationOrder.setPharmacyAdmin(pharmacyAdmin);
        medicationOrder.setStatus(MedicationOrderStatus.pending);
        medicationOrder.setVersion(1L);

        Medication medication = new Medication();
        medication.setId(1L);
        MedicationQuantity medicationQuantity = new MedicationQuantity();
        medicationQuantity.setQuantity(300);
        medicationQuantity.setMedication(medication);

        ArrayList<MedicationQuantity> medicationQuantities = new ArrayList<>();
        medicationQuantities.add(medicationQuantity);
        medicationOrder.setMedicationQuantity(medicationQuantities);

        //define pharmacy medication quantity
        MedicationQuantity medicationQuantityPharmacy = new MedicationQuantity();
        medicationQuantityPharmacy.setQuantity(3);
        medicationQuantityPharmacy.setMedication(medication);
        ArrayList<MedicationQuantity> medicationQuantitiesPharmacy = new ArrayList<>();
        medicationQuantitiesPharmacy.add(medicationQuantityPharmacy);
        pharmacy.setMedicationQuantity(medicationQuantitiesPharmacy);

        //define medication offer 1 and supplier 1
        MedicationOffer medicationOffer1 = new MedicationOffer();
        medicationOffer1.setId(1L);
        medicationOffer1.setShippingDate(LocalDateTime.now().plusDays(30));
        medicationOffer1.setMedicationOrder(medicationOrder);
        medicationOffer1.setStatus(MedicationOfferStatus.pending);
        medicationOffer1.setCost(32323);
        medicationOffer1.setVersion(1L);

        Supplier supplier1 = new Supplier();
        supplier1.setId(1L);

        MedicationQuantity medicationQuantitySupplier = new MedicationQuantity();
        medicationQuantitySupplier.setQuantity(320);
        medicationQuantitySupplier.setMedication(medication);
        List<MedicationQuantity> medicationQuantitiesSupplierList = new ArrayList<>();
        medicationQuantitiesSupplierList.add(medicationQuantitySupplier);
        supplier1.setMedicationQuantity(medicationQuantitiesSupplierList);

        List<MedicationOffer> medicationOffersSupplierList = new ArrayList<>();
        medicationOffersSupplierList.add(medicationOffer1);
        supplier1.setMedicationOffer(medicationOffersSupplierList);

        //define medication offer 2
        MedicationOffer medicationOffer2 = new MedicationOffer();
        medicationOffer2.setId(2L);
        medicationOffer2.setShippingDate(LocalDateTime.now().plusDays(23));
        medicationOffer2.setMedicationOrder(medicationOrder);
        medicationOffer2.setStatus(MedicationOfferStatus.pending);
        medicationOffer2.setCost(103000);
        medicationOffer2.setVersion(1L);

        //generate return offers for medication order
        ArrayList<MedicationOffer> medicationOffers = new ArrayList<>();
        medicationOffers.add(medicationOffer1);
        medicationOffers.add(medicationOffer2);

        MedicationOfferDTO dto = new MedicationOfferDTO(supplier1, medicationOffer1);

        ArrayList<Supplier> suppliers = new ArrayList<>();
        suppliers.add(supplier1);

        when(medicationOfferRepository.getMedicationOffersByMedicationOrder(dto.getMedicationOrderId())).thenReturn(medicationOffers);
        when(medicationOrderRepository.save(medicationOrder)).thenReturn(medicationOrder);

        when(medicationOrderRepository.findById(1L)).thenReturn(java.util.Optional.of(medicationOrder));
        when(medicationOrderRepository.findById(dto.getMedicationOrderId())).thenReturn(java.util.Optional.of(medicationOrder));

        when(medicationOfferRepository.save(medicationOffer1)).thenReturn(medicationOffer1);
        when(medicationOfferRepository.save(medicationOffer2)).thenReturn(medicationOffer2);

        when(pharmacyRepository.findById(medicationOrder.getPharmacyAdmin().getPharmacy().getId())).thenReturn(java.util.Optional.of(pharmacy));
        when(pharmacyRepository.save(pharmacy)).thenReturn(pharmacy);
        when(supplierRepository.findAll()).thenReturn(suppliers);
        //when(supplierService.getSupplierByMedicationOffer(medicationOffer1)).thenReturn(supplier1);
        when(supplierRepository.save(supplier1)).thenReturn(supplier1);

        acceptOffer(dto, 1L);
        assertThat(supplier1.getMedicationQuantity().get(0).getQuantity(), is(equalTo(20)));

    }

    private Boolean acceptOffer(MedicationOfferDTO medicationOfferDTO, Long pharmacyAdminId) {
        MedicationOrder medicationOrder = medicationOrderService.read(medicationOfferDTO.getMedicationOrderId()).get();
        if (!medicationOrder.getPharmacyAdmin().getId().equals(pharmacyAdminId))
            return false;


        if (!medicationOrder.getVersion().equals(medicationOfferDTO.getMedicationOrderVersion()))
            throw new ObjectOptimisticLockingFailureException("versions do not match", MedicationOrder.class);


        for (MedicationOffer medicationOffer : medicationOfferRepository.getMedicationOffersByMedicationOrder(medicationOfferDTO.getMedicationOrderId())) {
            if (medicationOffer.getId().equals(medicationOfferDTO.getId()) && medicationOffer.getStatus() == MedicationOfferStatus.pending) {

                medicationOffer.setStatus(MedicationOfferStatus.approved);
                if (!medicationOffer.getVersion().equals(medicationOfferDTO.getMedicationOfferVersion()))
                    throw new ObjectOptimisticLockingFailureException("versions do not match", MedicationOffer.class);

                medicationOfferRepository.save(medicationOffer);

                updatePharmacyMedicationQuantity(medicationOrder);
                updateSupplierMedicationQuantity(medicationOrder, medicationOffer);
                continue;
            }
            medicationOffer.setStatus(MedicationOfferStatus.rejected);
            medicationOfferRepository.save(medicationOffer);
        }
        medicationOrder.setStatus(MedicationOrderStatus.processed);
        return medicationOrderService.save(medicationOrder) != null;
    }

    private void updatePharmacyMedicationQuantity(MedicationOrder medicationOrder) {
        Pharmacy pharmacy = pharmacyService.read(medicationOrder.getPharmacyAdmin().getPharmacy().getId()).get();
        ArrayList<MedicationQuantity> medicationsToAdd = new ArrayList<>();
        for (MedicationQuantity medicationQuantityOrder : medicationOrder.getMedicationQuantity()) {
            boolean foundMedication = false;
            for (MedicationQuantity medicationQuantityPharmacy : pharmacy.getMedicationQuantity()) {
                if (medicationQuantityOrder.getMedication().getId().equals(medicationQuantityPharmacy.getMedication().getId())) {
                    medicationQuantityPharmacy.setQuantity(medicationQuantityPharmacy.getQuantity() + medicationQuantityOrder.getQuantity());
                    foundMedication = true;
                }
            }
            if (!foundMedication)
                medicationsToAdd.add(medicationQuantityOrder);
        }

        for (MedicationQuantity medicationQuantity : medicationsToAdd)
            pharmacy.getMedicationQuantity().add(new MedicationQuantity(medicationQuantity.getMedication(), medicationQuantity.getQuantity()));

        pharmacyService.save(pharmacy);
    }

    private void updateSupplierMedicationQuantity(MedicationOrder medicationOrder, MedicationOffer medicationOffer) {
        Supplier supplier = supplierService.getSupplierByMedicationOffer(medicationOffer);
        for (MedicationQuantity medicationQuantitySupplier : supplier.getMedicationQuantity())
            for (MedicationQuantity medicationQuantityOrder : medicationOrder.getMedicationQuantity())
                if (medicationQuantityOrder.getMedication().getId().equals(medicationQuantitySupplier.getMedication().getId()))
                    medicationQuantitySupplier.setQuantity(medicationQuantitySupplier.getQuantity() - medicationQuantityOrder.getQuantity());

        supplierService.save(supplier);
    }
}
