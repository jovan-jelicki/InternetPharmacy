package app.service;

import app.dto.AppointmentScheduledDTO;
import app.dto.EventDTO;
import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.user.EmployeeType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

public interface AppointmentService extends CRUDService<Appointment>{
    Collection<Appointment> getAllByExaminerAndAppointmentStatus(Long examinerId, EmployeeType type, AppointmentStatus status);

    Collection<AppointmentScheduledDTO> getAllAppointmentsByExaminer(Long examinerId, EmployeeType type);
    Collection<EventDTO> getAllEventsOfExaminer(Long examinerId, EmployeeType type);

    Boolean createAvailableAppointment(Appointment entity);

    Boolean finishAppointment(AppointmentScheduledDTO appointmentScheduledDTO);
        Collection<Appointment> getAllAppointmentsByExaminerIdAndType(Long examinerId, EmployeeType employeeType);

    Collection<Appointment> GetAllAvailableAppointmentsByPharmacy(Long pharmacyId);


}
