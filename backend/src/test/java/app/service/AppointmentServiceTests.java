package app.service;

import app.model.appointment.Appointment;
import app.model.time.Period;
import app.model.user.EmployeeType;
import app.repository.AppointmentRepository;
import app.repository.PharmacyRepository;
import app.service.impl.AppointmentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentServiceTests {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PharmacyRepository pharmacyRepository;

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



}
