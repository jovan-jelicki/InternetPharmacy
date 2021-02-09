package app.service;

import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.model.user.EmployeeType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateDermatologistAppointmentTest {


    @Autowired
    private AppointmentService appointmentService;
    public Appointment appointment1 = new Appointment();
    public Appointment appointment2 = new Appointment();
    public Pharmacy pharmacy = new Pharmacy();
    public boolean result1;
    public boolean result2;

    @Before
    public void setUp() throws Exception {
        pharmacy.setId(0L);
        appointment1.setActive(true);
        appointment1.setExaminerId(3L);
        appointment1.setPeriod(new Period(LocalDateTime.now().minusHours(7), LocalDateTime.now().minusHours(6)));
        appointment1.setPharmacy(pharmacy);
        appointment1.setType(EmployeeType.ROLE_dermatologist);
        appointment1.setPatient(null);
        appointment1.setAppointmentStatus(AppointmentStatus.available);

        appointment2.setActive(true);
        appointment2.setExaminerId(3L);
        appointment2.setPeriod(new Period(LocalDateTime.now().minusHours(7), LocalDateTime.now().minusHours(6)));
        appointment2.setPharmacy(pharmacy);
        appointment2.setType(EmployeeType.ROLE_dermatologist);
        appointment2.setPatient(null);
        appointment2.setAppointmentStatus(AppointmentStatus.available);
    }

    @Test
    public void testOptimisticLockingScenario() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                result1 =  appointmentService.createAvailableAppointment(appointment1);
                //System.out.println(appointment.getId());

            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                //try { Thread.sleep(1000); } catch (InterruptedException e) {}
                result2 = appointmentService.createAvailableAppointment(appointment2);

                //System.out.println(appointment.getId());
            }
        });
//        try {
//            future1.get(); // podize ExecutionException za bilo koji izuzetak iz prvog child threada
//        } catch (ExecutionException e) {
//            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
//            throw e.getCause();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        try {
            future2.get();
            future1.get();
        }
        catch (Exception ex) {
            System.out.println(appointment1.getId());
            System.out.println(appointment2.getId());
        }

        //System.out.println(appointment.getId());

        executor.shutdown();

    }
}
