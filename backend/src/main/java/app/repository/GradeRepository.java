package app.repository;

import app.model.grade.Grade;
import app.model.grade.GradeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Collection<Grade> findAllByPatient_Id(Long id);

    @Query("select avg(g.grade) from Grade g where g.gradedId = ?1 and g.gradeType = ?2")
    double findAverageGradeForEntity(Long id, GradeType gradeType);

    Grade findAllByPatient_IdAndGradedIdAndGradeType(Long patientId, Long gradedId, GradeType type);
}
