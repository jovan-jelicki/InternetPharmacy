package app.service;

import app.dto.AppointmentFinishedDTO;
import app.dto.AppointmentScheduledDTO;
import app.dto.EventDTO;
import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.user.EmployeeType;

import java.time.LocalDateTime;
import java.util.Collection;

public interface AppointmentService extends CRUDService<Appointment>{
    Collection<Appointment> getAllByExaminerAndAppointmentStatus(Long examinerId, EmployeeType type, AppointmentStatus status);

    Collection<AppointmentScheduledDTO> getAllAppointmentsByExaminer(Long examinerId, EmployeeType type);
    Collection<EventDTO> getAllEventsOfExaminer(Long examinerId, EmployeeType type);

    Boolean createAvailableAppointment(Appointment entity);

    Boolean finishAppointment(AppointmentScheduledDTO appointmentScheduledDTO);
        Collection<Appointment> getAllAppointmentsByExaminerIdAndType(Long examinerId, EmployeeType employeeType);

    Collection<Appointment> GetAllAvailableAppointmentsByPharmacy(Long pharmacyId);

    Collection<AppointmentFinishedDTO> getFinishedByExaminer(Long examinerId, EmployeeType type);

    Appointment scheduleCounseling(Appointment entity);
    Collection<Appointment> GetAllScheduledAppointmentsByExaminerIdAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date);

    Collection<Appointment> findAppointmentsByPatientNotNullAndType(EmployeeType type);

    Collection<Appointment> GetAllAvailableAppointmentsByExaminerIdTypeAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date);



}
