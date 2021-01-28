package app.model.grade;

import app.model.medication.Medication;
import app.model.user.Patient;

import javax.persistence.*;

@Entity
public class MedicationGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_grade_generator")
    @SequenceGenerator(name="medication_grade_generator", sequenceName = "medication_grade_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Medication medication;

    @ManyToOne
    @JoinColumn
    private Patient patient;

    @Column
    private int grade;

    public MedicationGrade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}