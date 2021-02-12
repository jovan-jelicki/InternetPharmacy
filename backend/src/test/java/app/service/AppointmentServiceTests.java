package app.service;

import app.dto.EventDTO;
import app.model.appointment.Appointment;
<<<<<<< HEAD
=======
import app.model.appointment.AppointmentStatus;
>>>>>>> develop
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
import java.util.ArrayList;
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

    @Test
    public void testGetAllEventsOfExaminer() {
        Collection<Appointment> appointments = getAppointments();
        Appointment appointment = appointments.stream().findFirst().get();
        when(appointmentRepository.getAllByExaminerAndAppointmentStatus(appointment.getExaminerId(), appointment.getType(), AppointmentStatus.available)).thenReturn(appointments);
        Collection<EventDTO>  eventDTOs = appointmentService.getAllEventsOfExaminer(appointment.getExaminerId(), appointment.getType());

        assertThat(eventDTOs.stream().findFirst().get().getStart(), is(equalTo(LocalDateTime.of(2021, 11,1,20,0))));
    }


    private Collection<Appointment> getAppointments(){
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(1l);
        pharmacy.setName("Belamedika");
        Appointment appointment = new Appointment();
        appointment.setPharmacy(pharmacy);
        appointment.setAppointmentStatus(AppointmentStatus.available);
        appointment.setActive(true);
        appointment.setType(EmployeeType.ROLE_dermatologist);
        appointment.setPeriod(new Period(LocalDateTime.of(2021, 11,1,20,0), LocalDateTime.of(2021, 11,1,21,0)));
        appointment.setExaminerId(3l);
        Collection<Appointment> retVal = new ArrayList<>();
        retVal.add(appointment);

        return retVal;
    }

}
