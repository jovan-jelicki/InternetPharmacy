package app.model.grade;

import app.model.user.Dermatologist;
import app.model.user.Patient;

import javax.persistence.*;

@Entity
public class DermatologistGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dermatologist_grade_generator")
    @SequenceGenerator(name="dermatologist_grade_generator", sequenceName = "dermatologist_grade_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Dermatologist dermatologist;

    @ManyToOne
    @JoinColumn
    private Patient patient;

    @Column
    private int grade;

    public DermatologistGrade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dermatologist getDermatologist() {
        return dermatologist;
    }

    public void setDermatologist(Dermatologist dermatologistId) {
        this.dermatologist = dermatologistId;
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