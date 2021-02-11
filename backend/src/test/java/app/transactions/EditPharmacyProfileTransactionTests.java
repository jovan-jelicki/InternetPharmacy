package app.transactions;

import app.model.pharmacy.Pharmacy;
import app.service.PharmacyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//Created by David

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EditPharmacyProfileTransactionTests {

    @Autowired
    private PharmacyService pharmacyService;



    //Test u kojem pokusavamo da imitiramo 2 korisnika koja su u isto vreme pokusala da zakazu pregled kod farmaceuta
    //Rezultat je da ce jedan korisnik uspesno zakazati, a drugi dobiti poruku da je dati termin zakazan.
    @Rollback(true)
    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void editPharmacyProfile() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final Boolean[] res1 = new Boolean[1];
        final Boolean[] res2 = new Boolean[1];
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            @Transactional
            public void run() {
                System.out.println("Startovan Thread 1");
                Pharmacy pharmacy = pharmacyService.read(2L).get();
                try { Thread.sleep(3000); } catch (InterruptedException e) {}

                pharmacy.setPharmacistCost(3000);

                res1[0] = pharmacyService.save(pharmacy) != null;
            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            @Transactional
            public void run() {
                System.out.println("Startovan Thread 2");
                Pharmacy pharmacy = pharmacyService.read(2L).get();
                pharmacy.setPharmacistCost(1000);

                res2[0] = pharmacyService.save(pharmacy) != null;
            }
        });
        try {

            future2.get();
            future1.get();

            if(res1[0] && res2[0]){
                throw new InterruptedException("Not good");
            }

        } catch (Exception e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        }
        executor.shutdown();
    }

}