package app.service;

import app.dto.MakeMedicationReservationDTO;
import app.model.appointment.Appointment;
import app.model.medication.MedicationQuantity;
import app.model.medication.MedicationReservation;
import app.model.medication.MedicationReservationStatus;
import app.model.pharmacy.Pharmacy;
import app.model.user.Patient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MedicationReservationTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PharmacistService pharmacistService;

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private MedicationReservationService medicationReservationService;

    @Rollback(true)
    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void createReservations() throws Throwable {
        MedicationReservation medicationReservation1 = new MedicationReservation();
        medicationReservation1.setStatus(MedicationReservationStatus.requested);
        Patient patient1 = new Patient();
        patient1.setId(0l);
        medicationReservation1.setPatient(patient1);
        medicationReservation1.setPickUpDate(LocalDateTime.of(2021, 5, 5, 8, 30, 0));
        MedicationQuantity medicationQuantity1 = new MedicationQuantity();
        medicationQuantity1.setQuantity(2);
        medicationQuantity1.setMedication(medicationService.read(0l).get());
        medicationReservation1.setMedicationQuantity(medicationQuantity1);

        MakeMedicationReservationDTO reservation1 = new MakeMedicationReservationDTO(medicationReservation1, 0l);

        MedicationReservation medicationReservation2 = new MedicationReservation();
        medicationReservation2.setStatus(MedicationReservationStatus.requested);
        Patient patient2 = new Patient();
        patient2.setId(6l);
        medicationReservation2.setPatient(patient2);
        medicationReservation2.setPickUpDate(LocalDateTime.of(2021, 5, 5, 8, 30, 0));
        MedicationQuantity medicationQuantity2 = new MedicationQuantity();
        medicationQuantity2.setQuantity(4);
        medicationQuantity2.setMedication(medicationService.read(0l).get());
        medicationReservation2.setMedicationQuantity(medicationQuantity1);

        MakeMedicationReservationDTO reservation2 = new MakeMedicationReservationDTO(medicationReservation1, 0l);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        final Boolean[] res1 = new Boolean[1];
        final Boolean[] res2 = new Boolean[1];

        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            @Transactional
            public void run() {
                System.out.println("Startovan Thread 1");
                try { Thread.sleep(3000); } catch (InterruptedException e) {}
                res1[0] = medicationReservationService.reserve(reservation1) != null;

            }
        });

        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            @Transactional
            public void run() {
                System.out.println("Startovan Thread 2");
                res2[0] = medicationReservationService.reserve(reservation2) != null;
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
