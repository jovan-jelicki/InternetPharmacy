package app.service;

import app.model.appointment.Appointment;
import app.model.user.EmployeeType;
import app.repository.AppointmentRepository;
import app.service.impl.AppointmentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentMethodsTests {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private Appointment appointment;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    @Transactional
    public void testSave(){
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        Appointment savedAppointment = appointmentRepository.save(this.appointment);

        assertThat(savedAppointment, is(equalTo(this.appointment)));

    }

    @Test
    public void testGetAllAvailableAppointmentsByExaminerIdTypeAfterDate(){
        when(appointmentRepository.GetAllAvailableAppointmentsByExaminerIdTypeAfterDate(1l, EmployeeType.ROLE_dermatologist, LocalDateTime.of(2021,2,1,12,50))).thenReturn(Arrays.asList(appointment));
        Collection<Appointment> appointments = appointmentRepository.GetAllAvailableAppointmentsByExaminerIdTypeAfterDate(1l, EmployeeType.ROLE_dermatologist, LocalDateTime.of(2021,2,1,12,50));

        assertThat(1, is(equalTo(appointments.size())));
    }
}
