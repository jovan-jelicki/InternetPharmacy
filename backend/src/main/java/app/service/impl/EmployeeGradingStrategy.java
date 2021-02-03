package app.service.impl;

import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.grade.Grade;
import app.model.grade.GradeType;
import app.model.grade.StrategyName;
import app.model.user.EmployeeType;
import app.repository.AppointmentRepository;
import app.repository.GradeRepository;
import app.repository.PatientRepository;
import app.service.GradingStrategy;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class EmployeeGradingStrategy implements GradingStrategy {
    private final GradeRepository gradeRepository;
    private final AppointmentRepository appointmentRepository;

    public EmployeeGradingStrategy(GradeRepository gradeRepository, AppointmentRepository appointmentRepository) {
        this.gradeRepository = gradeRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Grade grade(Grade grade) {
        if(hasAttendedAppointments(grade))
            gradeRepository.save(grade);
        return null;
    }

    private boolean hasAttendedAppointments(Grade grade) {
        return appointmentRepository
                .getAllByExaminerAndAppointmentStatus(grade.getGradedId(),
                        mapGradeTypeToEmployeeType(grade.getGradeType()), AppointmentStatus.patientPresent)
                .stream()
                .anyMatch(a -> a.getPatient().getId() == grade.getPatient().getId());
    }

    private EmployeeType mapGradeTypeToEmployeeType(GradeType gradeType) {
        return EmployeeType.valueOf(gradeType.name());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.employee;
    }
}
