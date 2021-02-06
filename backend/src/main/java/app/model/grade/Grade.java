package app.model.grade;

import app.model.user.Patient;

import javax.persistence.*;

@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dermatologist_grade_generator")
    @SequenceGenerator(name="dermatologist_grade_generator", sequenceName = "dermatologist_grade_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    @Column
    private Long gradedId;

    @Enumerated(EnumType.ORDINAL)
    private GradeType gradeType;

    @ManyToOne
    @JoinColumn
    private Patient patient;

    @Column
    private int grade;

    public Grade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGradedId() {
        return gradedId;
    }

    public void setGradedId(Long gradedId) {
        this.gradedId = gradedId;
    }

    public GradeType getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patientId) {
        this.patient = patientId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}