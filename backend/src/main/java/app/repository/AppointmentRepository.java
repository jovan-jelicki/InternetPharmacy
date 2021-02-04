package app.repository;

import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.user.EmployeeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


    @Query("select a from Appointment a where a.patient is not null and a.appointmentStatus = 0 and a.isActive = true and a.type = ?1")
    Collection<Appointment> getAllAvailableByType(EmployeeType type);

    @Query("select a from Appointment a where a.patient.id = ?1 and a.appointmentStatus = 0 and a.isActive = true and a.type = ?2")
    Collection<Appointment> findAppointmentsByPatientAndType(Long patientId, EmployeeType type);

    @Query("select a from Appointment a where a.patient.id = ?1 and a.appointmentStatus = 3 and a.isActive = false and a.type = ?2")
    Collection<Appointment> findCancelledByPatientIdAndType(Long patientId, EmployeeType type);

    @Query("select a from Appointment a where a.examinerId = ?1 and a.type = ?2 and a.appointmentStatus = 0 and a.patient is not null and a.isActive=true")
    Collection<Appointment> getAllScheduledNotFinishedByExaminer(Long examinerId, EmployeeType type);

    @Query("select a from Appointment a where a.patient.id = ?1 and a.appointmentStatus = ?2 and a.isActive=true")
    Collection<Appointment> getAllNotFinishedByPatientId(Long patientId,AppointmentStatus status);

    Collection<Appointment> getAllAppointmentsByExaminerIdAndType(Long examinerId, EmployeeType employeeType);

    @Query("select a from Appointment a where a.examinerId = ?1 and a.type = ?2 and a.appointmentStatus = ?3 and a.isActive=true")
    Collection<Appointment> getAllByExaminerAndAppointmentStatus(Long examinerId, EmployeeType type, AppointmentStatus status);

    @Query("select a from Appointment a where a.pharmacy.id = ?1 and a.appointmentStatus = 0 and a.isActive=true")
    Collection<Appointment> GetAllAvailableAppointmentsByPharmacy(Long pharmacyId);

    @Query("select a from Appointment a where a.patient is not null and a.examinerId = ?1 and a.type = ?2 and a.period.periodStart >= ?3 and a.isActive=true")
    Collection<Appointment> GetAllScheduledAppointmentsByExaminerIdAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date);

    @Query("select a from Appointment a where a.patient is null and a.examinerId = ?1 and a.type = ?2 and a.period.periodStart >= ?3 and  a.isActive=true")
    Collection<Appointment> GetAllAvailableAppointmentsByExaminerIdTypeAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date);

    @Query("select a from Appointment a where a.patient is not null and a.examinerId = ?1 and a.type = ?2 and a.period.periodStart >= ?3 and a.pharmacy.id=?4 and a.isActive=true")
    Collection<Appointment> GetAllScheduledAppointmentsByExaminerIdAndPharmacyAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date,Long pharmacyId);

    @Query("select a from Appointment a where a.patient is null and a.examinerId = ?1 and a.type = ?2 and a.period.periodStart >= ?3 and a.pharmacy.id=?4 and a.isActive=true")
    Collection<Appointment> GetAllAvailableAppointmentsByExaminerIdAndPharmacyAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date,Long pharmacyId);

    @Query("select a from Appointment a where a.patient is null and a.period.periodStart >= ?1 and a.pharmacy.id=?2 and a.type=0 and a.isActive=true")
    Collection<Appointment> getAllAvailableUpcomingDermatologistAppointmentsByPharmacy(LocalDateTime date,Long pharmacyId);

    @Query("select a from Appointment a where a.patient is not null and a.period.periodStart >= ?1 and a.period.periodEnd <= ?2 and a.pharmacy.id = ?3 and a.type=?4 and a.appointmentStatus=1 and a.isActive=true")
    Collection<Appointment> getSuccessfulAppointmentCountByPeriodAndEmployeeTypeAndPharmacy(LocalDateTime dateStart, LocalDateTime dateEnd, Long pharmacyId, EmployeeType employeeType);

    @Query("select a from Appointment  a where a.patient.id = ?1 and a.appointmentStatus = 1 and a.type = ?2")
    Collection<Appointment> getAllFinishedByPatientAndExaminerType(Long patientId, EmployeeType type);
}