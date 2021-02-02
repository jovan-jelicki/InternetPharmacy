package app.service.impl;

import app.model.grade.Grade;
import app.model.grade.GradeType;
import app.model.grade.StrategyName;
import app.repository.GradeRepository;
import app.service.GradeService;
import app.service.GradingStrategy;
import app.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final GradingStrategyFactory gradingStrategyFactory;
    private final PatientService patientService;

    private GradingStrategy gradingStrategy;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository, GradingStrategyFactory gradingStrategyFactory, PatientService patientService) {
        this.gradeRepository = gradeRepository;
        this.gradingStrategyFactory = gradingStrategyFactory;
        this.patientService = patientService;
    }

    @Override
    public Grade save(Grade grade) {
        GradeType gradeType = grade.getGradeType();
        if(gradeType == GradeType.dermatologist || gradeType == GradeType.pharmacist)
            gradingStrategy = gradingStrategyFactory.findStrategy(StrategyName.employee);
        else
            gradingStrategy = gradingStrategyFactory.findStrategy(StrategyName.asset);
        grade.setPatient(patientService.read(grade.getPatient().getId()).get());
        return gradingStrategy.grade(grade);
    }

    @Override
    public Collection<Grade> findAllByPatientId(Long id) {
        return gradeRepository.findAllByPatient_Id(id);
    }

    @Override
    public double findAverageGradeForEntity(Long id, GradeType gradeType) {
        return gradeRepository.findAverageGradeForEntity(id, gradeType);
    }

    @Override
    public void setGradingStrategy(GradingStrategy gradingStrategy) {

    }
}
