package app.model;

import javax.persistence.*;

@Entity
public class DermatologistGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Dermatologist dermatologistId;

    @ManyToOne
    @JoinColumn
    private Patient patientId;

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

    public Dermatologist getDermatologistId() {
        return dermatologistId;
    }

    public void setDermatologistId(Dermatologist dermatologistId) {
        this.dermatologistId = dermatologistId;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}