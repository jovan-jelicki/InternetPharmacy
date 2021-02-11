package app.service;

import app.model.appointment.Appointment;
import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.model.user.EmployeeType;
import app.model.user.Patient;
import app.repository.AppointmentRepository;
import app.repository.PharmacyRepository;
import app.service.impl.AppointmentServiceImpl;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentServiceTests {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PharmacyRepository pharmacyRepository;

    @Mock
    private PatientService patientService;

    @Mock
    private Appointment appointmentMock;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    @Transactional
    public void testSave(){
        when(appointmentRepository.save(appointmentMock)).thenReturn(appointmentMock);

        Appointment savedAppointment = appointmentRepository.save(this.appointmentMock);

        assertThat(savedAppointment, is(equalTo(this.appointmentMock)));

    }

    @Test
    public void testGetAllAvailableAppointmentsByExaminerIdTypeAfterDate(){
        when(appointmentRepository.GetAllAvailableAppointmentsByExaminerIdTypeAfterDate(1l, EmployeeType.ROLE_dermatologist, LocalDateTime.of(2021,2,1,12,50))).thenReturn(Arrays.asList(appointmentMock));
        Collection<Appointment> appointments = appointmentRepository.GetAllAvailableAppointmentsByExaminerIdTypeAfterDate(1l, EmployeeType.ROLE_dermatologist, LocalDateTime.of(2021,2,1,12,50));

        assertThat(1, is(equalTo(appointments.size())));
    }

    //Negativni

    //Nije dozvoljen upis bez pharmacy
    @Test(expected = NullPointerException.class)
    @Transactional
    public void testSave2(){
        Period period = new Period(LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        Appointment appointment = new Appointment();
        appointment.setPeriod(period);
        appointment.setExaminerId(1l);
        appointment.setType(EmployeeType.ROLE_dermatologist);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        Appointment savedAppointment = appointmentService.save(appointment);
        verify(appointmentRepository, times(1)).save(appointment);
        verifyNoMoreInteractions(appointmentRepository);
    }

    @Test
    public void scheduleCounselingTest_NoPenalties() {
        Appointment appointment = new Appointment();
        Period period = new Period();
        period.setPeriodStart(LocalDateTime.of(2021, 5, 6, 10, 0, 0));
        period.setPeriodEnd(LocalDateTime.of(2021, 5, 6, 11, 0, 0));
        appointment.setPeriod(period);
        Patient patient0 = new Patient();
        patient0.setId(0l);
        patient0.setPenaltyCount(0);
        Pharmacy pharmacy0 = new Pharmacy();
        pharmacy0.setId(0l);
        appointment.setPharmacy(pharmacy0);
        appointment.setPatient(patient0);
        when(patientService.read(0l)).thenReturn(Optional.of(patient0));
        when(pharmacyRepository.findById(0l)).thenReturn(Optional.of(pharmacy0));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        Appointment actual = appointmentService.scheduleCounseling(appointment);

        Assert.notNull(actual);
    }

    @Test
    public void scheduleCounselingTest_WithPenalties() {
        Appointment appointment = new Appointment();
        Period period = new Period();
        period.setPeriodStart(LocalDateTime.of(2021, 5, 6, 10, 0, 0));
        period.setPeriodEnd(LocalDateTime.of(2021, 5, 6, 11, 0, 0));
        appointment.setPeriod(period);
        Patient patient0 = new Patient();
        patient0.setId(0l);
        patient0.setPenaltyCount(3);
        Pharmacy pharmacy0 = new Pharmacy();
        pharmacy0.setId(0l);
        appointment.setPharmacy(pharmacy0);
        appointment.setPatient(patient0);
        when(patientService.read(0l)).thenReturn(Optional.of(patient0));
        when(pharmacyRepository.findById(0l)).thenReturn(Optional.of(pharmacy0));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        Appointment actual = appointmentService.scheduleCounseling(appointment);

        Assert.isNull(actual);
    }

}
