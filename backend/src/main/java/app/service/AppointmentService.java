package app.service;

import app.model.appointment.Appointment;
import app.model.user.EmployeeType;

import java.util.Collection;

public interface AppointmentService extends CRUDService<Appointment>{
    Boolean createAvailableAppointment(Appointment entity);

    Collection<Appointment> getAllAppointmentsByExaminerIdAndType(Long examinerId, EmployeeType employeeType);
}
