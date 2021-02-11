package app.service;

import app.dto.VacationRequestDTO;
import app.model.appointment.Appointment;
import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.model.time.VacationRequest;
import app.model.time.VacationRequestStatus;
import app.model.user.Credentials;
import app.model.user.Dermatologist;
import app.model.user.EmployeeType;
import app.repository.AppointmentRepository;
import app.repository.PharmacyRepository;
import app.repository.VacationRequestRepository;
import app.service.impl.AppointmentServiceImpl;
import app.service.impl.VacationRequestServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


//Created by David
@RunWith(SpringRunner.class)
@SpringBootTest
public class VacationRequestTests {

    @Mock
    private VacationRequestRepository vacationRequestRepository;

    @Mock
    private PharmacyRepository pharmacyRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentServiceImpl appointmentService;

    @InjectMocks
    private VacationRequestServiceImpl vacationRequestService;






    private void fillData() {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(1L);

        VacationRequest vacationRequest = new VacationRequest();
        vacationRequest.setId(1L);
        vacationRequest.setEmployeeId(3L);
        Period period = new Period(LocalDateTime.of(2021,2,14,12,0), LocalDateTime.of(2021,2,27,13,0) );
        vacationRequest.setPeriod(period);
        vacationRequest.setPharmacy(pharmacy);
        vacationRequest.setEmployeeType(EmployeeType.ROLE_dermatologist);
    }

    @Test
    @Transactional
    public void testConfirmVacationRequest(){
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(1L);

        Dermatologist dermatologist = new Dermatologist();
        dermatologist.setFirstName("David");
        dermatologist.setLastName("Drvar");
        Credentials credentials = new Credentials();
        credentials.setEmail("david@gmail.com");
        dermatologist.setCredentials(credentials);

        VacationRequest vacationRequest = new VacationRequest();
        vacationRequest.setId(1L);
        vacationRequest.setEmployeeId(3L);
        Period period = new Period(LocalDateTime.of(2021,2,14,12,0), LocalDateTime.of(2021,2,27,13,0) );
        vacationRequest.setPeriod(period);
        vacationRequest.setPharmacy(pharmacy);
        vacationRequest.setEmployeeType(EmployeeType.ROLE_dermatologist);
        vacationRequest.setVacationRequestStatus(VacationRequestStatus.requested);
        vacationRequest.setVersion(1L);

        VacationRequestDTO vacationRequestDTO = new VacationRequestDTO(dermatologist, vacationRequest);

        when(vacationRequestService.read(vacationRequestDTO.getId())).thenReturn(java.util.Optional.of(vacationRequest));

        Collection<Appointment> appointments = setupAppointmentService();

        doReturn(appointments).when(appointmentService).GetAllScheduledAppointmentsByExaminerIdAfterDate(vacationRequestDTO.getEmployeeId(),vacationRequestDTO.getEmployeeType(), vacationRequestDTO.getPeriod().getPeriodStart());

//        when(appointmentService.
//                GetAllScheduledAppointmentsByExaminerIdAfterDate(vacationRequestDTO.getEmployeeId(),vacationRequestDTO.getEmployeeType(), vacationRequestDTO.getPeriod().getPeriodStart()))
//                .thenReturn(appointments);

        assertThat(confirmVacationRequest(vacationRequestDTO), is(equalTo(false)));
    }

    private Collection<Appointment> setupAppointmentService() {
        Appointment appointment = new Appointment();
        appointment.setExaminerId(3L);
        appointment.setType(EmployeeType.ROLE_dermatologist);
        Period period = new Period(LocalDateTime.of(2021,2,16,12,0), LocalDateTime.of(2021,2,16,13,0) );
        appointment.setPeriod(period);

        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);

        return appointments;

    }

    public boolean confirmVacationRequest(VacationRequestDTO vacationRequestDTO) {
        VacationRequest vacationRequest = vacationRequestService.read(vacationRequestDTO.getId()).get();

        if (!vacationRequestDTO.getVersion().equals(vacationRequest.getVersion()))
            throw new ObjectOptimisticLockingFailureException("versions do not match", VacationRequest.class);

        //check if there are any scheduled pharmacist appointments for that period
        Collection<Appointment> scheduledAppointmentsForVacationRequestPeriod = appointmentService.
                GetAllScheduledAppointmentsByExaminerIdAfterDate(vacationRequestDTO.getEmployeeId(),vacationRequestDTO.getEmployeeType(), vacationRequestDTO.getPeriod().getPeriodStart())
                .stream().filter(appointment -> appointment.getPeriod().getPeriodEnd().isBefore(vacationRequestDTO.getPeriod().getPeriodEnd())).collect(Collectors.toList());

        if (scheduledAppointmentsForVacationRequestPeriod.size() != 0)
            return false;

        //delete available appointments for that period
        Collection<Appointment> availableAppointmentsForVacationRequestPeriod = appointmentService.
                GetAllAvailableAppointmentsByExaminerIdTypeAfterDate(vacationRequestDTO.getEmployeeId(),vacationRequestDTO.getEmployeeType(), vacationRequestDTO.getPeriod().getPeriodStart())
                .stream().filter(appointment -> appointment.getPeriod().getPeriodEnd().isBefore(vacationRequestDTO.getPeriod().getPeriodEnd())).collect(Collectors.toList());

        for (Appointment appointment : availableAppointmentsForVacationRequestPeriod) {
            appointment.setActive(false);
            appointmentService.save(appointment);
        }

        vacationRequest.setVacationRequestStatus(VacationRequestStatus.approved);
        return vacationRequestService.save(vacationRequest) != null;
    }
}
