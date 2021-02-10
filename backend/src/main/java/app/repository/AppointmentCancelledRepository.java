package app.repository;

import app.model.appointment.AppointmentCancelled;
import app.model.appointment.AppointmentStatus;
import app.model.user.EmployeeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AppointmentCancelledRepository extends JpaRepository<AppointmentCancelled, Long> {
    Collection<AppointmentCancelled> findAllByPatient_IdAndType(Long id, EmployeeType type);
}
