package app.repository;

import app.model.appointment.Appointment;
import app.model.user.EmployeeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Collection<Appointment> findAppointmentsByPatientNotNull();

    Collection<Appointment> getAllAppointmentsByExaminerIdAndType(Long examinerId, EmployeeType employeeType);
}
