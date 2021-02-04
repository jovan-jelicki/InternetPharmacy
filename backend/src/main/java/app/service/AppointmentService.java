package app.service;

import app.dto.*;
import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.user.EmployeeType;

import java.time.LocalDateTime;
import java.util.Collection;

public interface AppointmentService extends CRUDService<Appointment>{

    Collection<Appointment> getAllFinishedByPatientAndExaminerType(Long patientId, EmployeeType type);
    Appointment cancelCounseling(Long appointmentId);

    Collection<Appointment> getAllByExaminerAndAppointmentStatus(Long examinerId, EmployeeType type, AppointmentStatus status);

     Collection<Appointment> getAllNotFinishedByPatientId(Long patientId);

        Collection<AppointmentScheduledDTO> getAllAppointmentsByExaminer(Long examinerId, EmployeeType type);
    Collection<EventDTO> getAllEventsOfExaminer(Long examinerId, EmployeeType type);

    Boolean createAvailableAppointment(Appointment entity);

    Boolean finishAppointment(AppointmentScheduledDTO appointmentScheduledDTO);
        Collection<Appointment> getAllAppointmentsByExaminerIdAndType(Long examinerId, EmployeeType employeeType);

    Collection<Appointment> GetAllAvailableAppointmentsByPharmacy(Long pharmacyId);

    Collection<AppointmentFinishedDTO> getFinishedByExaminer(Long examinerId, EmployeeType type);

    void update(AppointmentUpdateDTO appointmentDTO);

    Appointment scheduleCounseling(Appointment entity);
    Collection<Appointment> GetAllScheduledAppointmentsByExaminerIdAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date);

    Collection<Appointment> findAppointmentsByPatientNotNullAndType(EmployeeType type);

    Collection<Appointment> findCancelledByPatientIdAndType(Long id, EmployeeType type);

    Collection<Appointment> GetAllAvailableAppointmentsByExaminerIdTypeAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date);

    Collection<Appointment> GetAllAvailableAppointmentsByExaminerIdAndPharmacyAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date, Long pharmacyId);

    Collection<Appointment> GetAllScheduledAppointmentsByExaminerIdAndPharmacyAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date, Long pharmacyId);

    Collection<Appointment> findAppointmentsByPatient_IdAndType(Long id, EmployeeType type);

    Boolean patientDidNotShowUp(Long id);
    Collection<Appointment> getAllAvailableUpcomingDermatologistAppointmentsByPharmacy(Long pharmacyId);

    boolean validateAppointmentTimeRegardingWorkingHours(Appointment entity);
     boolean validateAppointmentTimeRegardingAllWorkingHours(Appointment entity);

    boolean validateAppointmentTimeRegardingVacationRequests(Appointment entity);


    Collection<ReportsDTO> getAppointmentsMonthlyReport(Long pharmacyId);

    Collection<ReportsDTO> getAppointmentsQuarterlyReport(Long pharmacyId);

    Collection<ReportsDTO> getAppointmentsYearlyReport(Long pharmacyId);

    Collection<Appointment> getSuccessfulAppointmentCountByPeriodAndEmployeeTypeAndPharmacy(LocalDateTime dateStart, LocalDateTime dateEnd ,Long pharmacyId, EmployeeType employeeType);




    }
