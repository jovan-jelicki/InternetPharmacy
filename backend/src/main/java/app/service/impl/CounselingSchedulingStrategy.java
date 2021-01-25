package app.service.impl;

import app.model.appointment.Appointment;
import app.model.user.EmployeeType;
import app.model.user.Pharmacist;
import app.service.SchedulingStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class CounselingSchedulingStrategy implements SchedulingStrategy {
    @Override
    public Collection<Appointment> findAvailable(Collection<Appointment> appointments, LocalDateTime dateTime) {
        Collection<Appointment> available = new ArrayList<>();
        appointments.forEach(a -> {
                Pharmacist pharmacist = (Pharmacist) a.getExaminer();
                if(pharmacist.isOverlapping(dateTime) && !a.isOverlapping(dateTime))
                    available.add(a);
        });
        return  available;
    }
}
