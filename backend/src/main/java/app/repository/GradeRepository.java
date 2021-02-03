package app.repository;

import app.model.grade.Grade;
import app.model.grade.GradeType;
import net.bytebuddy.matcher.CollectionOneToOneMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    Collection<Grade> findAllByPatient_Id(Long id);

    @Query("select avg(g.grade) from Grade g where g.gradedId = ?1 and g.gradeType = ?2")
    double findAverageGradeForEntity(Long id, GradeType gradeType);

    @Query("select g from Grade g where g.patient.id = ?1 and g.gradedId = ?2")
    Grade findPatientGradeByGradedId(Long patientId, Long gradedId);
}
