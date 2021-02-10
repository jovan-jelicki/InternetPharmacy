package app.service;

import app.model.time.VacationRequest;
import app.model.time.VacationRequestStatus;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VacationRequestTests {

    @Autowired
    private VacationRequestService vacationRequestService;



    //Test u kojem pokusavamo da imitiramo 2 korisnika koja su u isto vreme pokusala da zakazu pregled kod farmaceuta
    //Rezultat je da ce jedan korisnik uspesno zakazati, a drugi dobiti poruku da je dati termin zakazan.
    @Rollback(true)
    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void vacationRequestEditing() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final Boolean[] res1 = new Boolean[1];
        final Boolean[] res2 = new Boolean[1];
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            @Transactional
            public void run() {
                System.out.println("Startovan Thread 1");
                VacationRequest vacationRequest = vacationRequestService.read(1L).get();
                try { Thread.sleep(3000); } catch (InterruptedException e) {}

                vacationRequest.setVacationRequestStatus(VacationRequestStatus.approved);

                res1[0] = vacationRequestService.save(vacationRequest) != null;
            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            @Transactional
            public void run() {
                System.out.println("Startovan Thread 2");
                VacationRequest vacationRequest = vacationRequestService.read(1L).get();

                vacationRequest.setVacationRequestStatus(VacationRequestStatus.rejected);

                res2[0] = vacationRequestService.save(vacationRequest) != null;
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