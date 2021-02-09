package app.integrationTests;

import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.appointment.Therapy;
import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.model.user.EmployeeType;
import app.model.user.Patient;
import app.service.AppointmentService;
import app.service.CounselingService;
import app.service.PatientService;
import app.service.PharmacyService;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.parameters.P;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentMethodsTests {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CounselingService counselingService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PharmacyService pharmacyService;

    @Transactional
    @Rollback(true)
    @Test
    public void testOptimisticLockingScenario() throws Throwable {
       /* ExecutorService executor = Executors.newFixedThreadPool(2);
        Appointment appointment = new Appointment();
        appointment.setTherapy(new Therapy());
        appointment.setPharmacy(pharmacyService.read(1l).get());
        appointment.setAppointmentStatus(AppointmentStatus.available);
        appointment.setPatient(patientService.read(0l).get());
        appointment.setActive(true);
        Period period = new Period();
        period.setPeriodEnd(LocalDateTime.now().plusHours(1));
        period.setPeriodStart(LocalDateTime.now());
        appointment.setPeriod(period);
        appointment.setExaminerId(1l);
        appointment.setType(EmployeeType.ROLE_pharmacist);
        Appointment appointment2 = new Appointment();
        appointment2.setTherapy(new Therapy());
        appointment2.setPharmacy(pharmacyService.read(1l).get());
        appointment2.setAppointmentStatus(AppointmentStatus.available);
        appointment2.setPatient(patientService.read(0l).get());
        appointment2.setActive(true);
        Period period2 = new Period();
        period2.setPeriodEnd(LocalDateTime.now().plusHours(1));
        period2.setPeriodStart(LocalDateTime.now());
        appointment2.setPeriod(period2);
        appointment2.setExaminerId(1l);
        appointment2.setType(EmployeeType.ROLE_pharmacist);
        final Boolean[] res1 = new Boolean[1];
        final Boolean[] res2 = new Boolean[1];
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                res1[0] = counselingService.pharmacistScheduling(appointment);
            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                res2[0] = counselingService.pharmacistScheduling(appointment2);
            }
        });
        try {
            future1.get();
            future2.get();
            if(res1[0] == res2[0]){
                throw new InterruptedException("Lose");
            }

        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();*/
    }

}
