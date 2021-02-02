package app.service;

import app.model.grade.Grade;
import app.model.grade.GradeType;

import java.util.Collection;

public interface GradeService {
    public Grade save(Grade grade);
    Collection<Grade> findAllByPatientId(Long id);
    double findAverageGradeForEntity(Long id, GradeType gradeType);
    void setGradingStrategy(GradingStrategy gradingStrategy);
}
