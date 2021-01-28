package app.service;

import app.dto.EventDTO;
import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.user.EmployeeType;

import java.util.Collection;

public interface AppointmentService extends CRUDService<Appointment>{
    Collection<Appointment> getAllByExaminerAndAppointmentStatus(Long examinerId, EmployeeType type, AppointmentStatus status);
    Collection<EventDTO> getAllEventsOfExaminer(Long examinerId, EmployeeType type);
    Boolean createAvailableAppointment(Appointment entity);

    Collection<Appointment> getAllAppointmentsByExaminerIdAndType(Long examinerId, EmployeeType employeeType);
}
