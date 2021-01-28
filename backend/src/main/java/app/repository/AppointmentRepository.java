package app.repository;

import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.user.EmployeeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Collection<Appointment> findAppointmentsByPatientNotNull();

    @Query("select a from Appointment a where a.examinerId = ?1 and a.type = ?2 and ")
    Collection<Appointment> getAllScheduledNotFinishedByExaminer(Long examinerId, EmployeeType type);

    @Query("select a from Appointment a where a.examinerId = ?1 and a.type = ?2 and a.appointmentStatus = ?3")
    Collection<Appointment> getAllByExaminerAndAppointmentStatus(Long examinerId, EmployeeType type, AppointmentStatus status);
}
