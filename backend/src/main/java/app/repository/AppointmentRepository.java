package app.repository;

import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.user.EmployeeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Collection<Appointment> findAppointmentsByPatientNotNullAndType(EmployeeType type);

    @Query("select a from Appointment a where a.examinerId = ?1 and a.type = ?2 and a.appointmentStatus = 0 and a.patient is not null and a.isActive=true")
    Collection<Appointment> getAllScheduledNotFinishedByExaminer(Long examinerId, EmployeeType type);

    Collection<Appointment> getAllAppointmentsByExaminerIdAndType(Long examinerId, EmployeeType employeeType);

    @Query("select a from Appointment a where a.examinerId = ?1 and a.type = ?2 and a.appointmentStatus = ?3 and a.isActive=true")
    Collection<Appointment> getAllByExaminerAndAppointmentStatus(Long examinerId, EmployeeType type, AppointmentStatus status);

    @Query("select a from Appointment a where a.pharmacy.id = ?1 and a.appointmentStatus = 0 and a.isActive=true")
    Collection<Appointment> GetAllAvailableAppointmentsByPharmacy(Long pharmacyId);

    @Query("select a from Appointment a where a.patient is not null and a.examinerId = ?1 and a.type = ?2 and a.period.periodStart >= ?3 and a.isActive=true")
    Collection<Appointment> GetAllScheduledAppointmentsByExaminerIdAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date);

    @Query("select a from Appointment a where a.patient is null and a.examinerId = ?1 and a.type = ?2 and a.period.periodStart >= ?3 and  a.isActive=true")
    Collection<Appointment> GetAllAvailableAppointmentsByExaminerIdTypeAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date);
}