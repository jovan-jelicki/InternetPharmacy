package app.service;

import app.model.grade.Grade;
import app.model.grade.GradeType;
import app.model.user.Patient;
import app.repository.GradeRepository;
import app.service.impl.GradeServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GradeServiceTest {

    @Mock
    GradeRepository gradeRepository;

    @Mock
    PatientService patientService;

    @InjectMocks
    GradeServiceImpl gradeService;


    @Test
    public void saveGradeTest() {
        Grade grade = new Grade();
        grade.setGrade(5);
        Grade savedGrade = new Grade();
        savedGrade.setGrade(5);
        savedGrade.setId(500l);
        Patient patient = new Patient();
        patient.setId(0l);
        savedGrade.setPatient(patient);
        when(gradeRepository.save(grade)).thenReturn(savedGrade);
        when(patientService.read(0l)).thenReturn(Optional.of(patient));

        Grade actualGrade = gradeRepository.save(grade);

        Assert.assertEquals(actualGrade.getGrade(), 5);
        Assert.assertNotNull(actualGrade.getPatient());
        Assert.assertNotNull(actualGrade.getId());
    }

    @Test
    public void findAverageGradeForEntityTest() {
        double average = 4.3;
        when(gradeRepository.findAverageGradeForEntity(0l, GradeType.pharmacy)).thenReturn(4.3);

        double actual = gradeService.findAverageGradeForEntity(0l, GradeType.pharmacy);

        Assert.assertEquals(4.3, actual, 0.5);
    }

}
