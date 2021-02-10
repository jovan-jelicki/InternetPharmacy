package app.service;

import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.appointment.Therapy;
import app.model.time.Period;
import app.model.user.EmployeeType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchedulingMethodsTestsForTransactions {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CounselingService counselingService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PharmacyService pharmacyService;

    private Appointment[] getTwoSameAppointments(){
        Appointment appointment = new Appointment();
        appointment.setTherapy(new Therapy());
        appointment.setPharmacy(pharmacyService.read(1l).get());
        appointment.setAppointmentStatus(AppointmentStatus.available);
        appointment.setPatient(patientService.read(0l).get());
        appointment.setActive(true);
        Period period = new Period();
        period.setPeriodEnd(LocalDateTime.of(2021,3,20, 11,0,0,0));
        period.setPeriodStart(LocalDateTime.of(2021,3,20, 10,0,0,0));
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
        period2.setPeriodEnd(LocalDateTime.of(2021,3,20, 11,0,0,0));
        period2.setPeriodStart(LocalDateTime.of(2021,3,20, 10,0,0,0));
        appointment2.setPeriod(period2);
        appointment2.setExaminerId(1l);
        appointment2.setType(EmployeeType.ROLE_pharmacist);
        Appointment[] appointments = new Appointment[] {appointment2, appointment};
        return appointments;

    }

    //Test u kojem pokusavamo da imitiramo 2 korisnika koja su u isto vreme pokusala da zakazu pregled kod farmaceuta
    //Rezultat je da ce jedan korisnik uspesno zakazati, a drugi dobiti poruku da je dati termin zakazan.
    @Transactional
    @Rollback(true)
    @Test(expected = DataIntegrityViolationException.class)
    public void pharmacistScheduling() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Appointment[] appointments = getTwoSameAppointments();
        final Boolean[] res1 = new Boolean[1];
        final Boolean[] res2 = new Boolean[1];
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                res1[0] = counselingService.pharmacistScheduling(appointments[0]);
            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                res2[0] = counselingService.pharmacistScheduling(appointments[1]);
            }
        });
        try {
            future1.get();
            future2.get();
            if(res1[0] == res2[0]){
                throw new InterruptedException("Lose");
            }

        } catch (Exception e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        }
        executor.shutdown();
    }

}
