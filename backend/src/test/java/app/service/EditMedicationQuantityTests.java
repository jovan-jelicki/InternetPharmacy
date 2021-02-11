package app.service;

import app.model.medication.MedicationQuantity;
import app.model.pharmacy.Pharmacy;
import app.repository.PharmacyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//Created by David
//the test doesnt work because of lazy exception, save to delete
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EditMedicationQuantityTests {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CounselingService counselingService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private PharmacyRepository pharmacyRepository;


    //Test u kojem pokusavamo da imitiramo 2 korisnika koja su u isto vreme pokusala da zakazu pregled kod farmaceuta
    //Rezultat je da ce jedan korisnik uspesno zakazati, a drugi dobiti poruku da je dati termin zakazan.
    @Rollback(true)

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void medicationQuantityEditing() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final Boolean[] res1 = new Boolean[1];
        final Boolean[] res2 = new Boolean[1];
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            @Transactional
            public void run() {
                System.out.println("Startovan Thread 1");
                try { Thread.sleep(3000); } catch (InterruptedException e) {}
                Pharmacy pharmacy = pharmacyService.read(0L).get();
                try { Thread.sleep(3000); } catch (InterruptedException e) {}
                Collection<MedicationQuantity> medicationQuantityCollection = pharmacyService.getPharmacyMedicationQuantity(pharmacy);

                MedicationQuantity medicationQuantity = medicationQuantityCollection.stream().
                        filter(medicationQuantityPharmacy -> medicationQuantityPharmacy.getId() == 2)
                        .findFirst().get();

                medicationQuantity.setQuantity(300);

                res1[0] = pharmacyService.save(pharmacy) != null;
            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            @Transactional
            public void run() {
                System.out.println("Startovan Thread 2");
                Pharmacy pharmacy = pharmacyService.read(0L).get();
                try { Thread.sleep(3000); } catch (InterruptedException e) {}
                MedicationQuantity medicationQuantity = pharmacyService.getPharmacyMedicationQuantity(pharmacy).stream().
                        filter(medicationQuantityPharmacy -> medicationQuantityPharmacy.getId() == 2)
                        .findFirst().get();

                medicationQuantity.setQuantity(800);


                res2[0] = pharmacyService.save(pharmacy) != null;
            }
        });
        try {
            future1.get();
            future2.get();
            if(res1[0] && res2[0]){
                throw new InterruptedException("Lose");
            }

        } catch (Exception e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        }
        executor.shutdown();
    }

}

//    Pharmacy pharmacy = pharmacyService.read(0L).get();
//    PharmacyMedicationListingDTO dto = new PharmacyMedicationListingDTO();
//                dto.setPharmacyId(pharmacy.getId());
//                        dto.setMedicationId(0L);
//                        dto.setMedicationQuantityId(2L);
//                        dto.setQuantity(500);
//                        res1[0] = pharmacyService.editMedicationQuantity(dto);