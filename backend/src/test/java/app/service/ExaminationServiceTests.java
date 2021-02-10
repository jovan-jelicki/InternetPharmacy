package app.service;


import app.dto.AppointmentUpdateDTO;
import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.model.user.Patient;
import app.repository.AppointmentRepository;
import app.repository.PatientRepository;
import app.repository.PharmacyRepository;
import app.service.impl.AppointmentServiceImpl;
import app.service.impl.ExaminationServiceImpl;
import app.service.impl.PatientServiceImpl;
import app.service.impl.PharmacyServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExaminationServiceTests {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PharmacyRepository pharmacyRepository;

    @Mock
    private PatientServiceImpl patientService;

    @Mock
    private AppointmentServiceImpl appointmentService;

    @Mock
    private PharmacyServiceImpl pharmacyService;

    @InjectMocks
    private ExaminationServiceImpl examinationService;

    private Pharmacy pharmacy;

    private Period period;

    private Appointment appointment;

    private Patient patient;

    private void fillData(){
        patient = new Patient();
        patient.setId(1l);
        appointment = new Appointment();
        appointment.setId(1l);
        appointment.setActive(true);
        pharmacy = new Pharmacy();
        pharmacy.setId(1l);
        period = new Period(LocalDateTime.of(2021,2,10,12,0), LocalDateTime.of(2021,2,10,13,0) );
    }

    @Test
    public void testDermatologistSchedulingCreatedAppointment(){
        fillData();
        when(appointmentService.read(1l)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.getAllNotFinishedByPatientId(1l, AppointmentStatus.available)).thenReturn(new ArrayList<>());
        when(pharmacyRepository.findById(1l)).thenReturn(Optional.of(pharmacy));
        when(patientService.read(1l)).thenReturn(Optional.of(patient));

        AppointmentUpdateDTO appointmentUpdateDTO = new AppointmentUpdateDTO();
        appointmentUpdateDTO.setPatientId(1l);
        appointmentUpdateDTO.setAppointmentId(1l);
        Boolean successfuly = examinationService.dermatologistSchedulingCreatedAppointment(appointmentUpdateDTO);

        assertThat(successfuly, is(equalTo(true)));

    }

}
