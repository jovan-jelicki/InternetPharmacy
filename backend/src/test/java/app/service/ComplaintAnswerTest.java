package app.service;

import app.model.complaint.Complaint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ComplaintAnswerTest {
    @Autowired
    private ComplaintService complaintService;

    @Rollback(true)
    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void complaintAnswer() throws  Throwable{
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final Boolean[] res1 = new Boolean[1];
        final Boolean[] res2 = new Boolean[1];

        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                Complaint complaint=complaintService.read(0L).get();
                complaint.setActive(false);

                try { Thread.sleep(3000); } catch (InterruptedException e) {}
                res1[0]=complaintService.save(complaint)!=null;
            }
        });

        Future<?> future2 = executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                Complaint complaint=complaintService.read(0L).get();
                complaint.setActive(false);
                res2[0]=complaintService.save(complaint)!=null;
            }
        });

        try {
            future2.get();//prolazi
            future1.get(); //baca  ObjectOptimisticLockingFailureException

            if(res1[0] && res2[0]){
                throw new InterruptedException("Not good");
            }

        } catch (Exception e) {
            System.out.println("Exception from thread " + e.getCause().getClass());
            throw e.getCause();
        }
        executor.shutdown();
    }
}
