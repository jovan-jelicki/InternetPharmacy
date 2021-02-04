package app.service;

import app.dto.grade.AssetGradeDTO;
import app.dto.grade.EmployeeGradeDTO;
import app.model.grade.Grade;
import app.model.grade.GradeType;

import java.util.Collection;

public interface GradeService {
    Grade save(Grade grade);
    Collection<EmployeeGradeDTO> findDermatologistsPatientCanGrade(Long patientId);
    Collection<EmployeeGradeDTO> findPharmacistPatientCanGrade(Long patientId);
    Collection<AssetGradeDTO> findMedicationsPatientCanGrade(Long patientId);
    Collection<AssetGradeDTO> findPharmacyPatientCanGrade(Long patientId);
    Collection<Grade> findAllByPatientId(Long id);
    double findAverageGradeForEntity(Long id, GradeType gradeType);
}
