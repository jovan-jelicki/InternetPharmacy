package app.model.grade;

import app.model.pharmacy.Pharmacy;
import app.model.user.Patient;

import javax.persistence.*;

@Entity
public class PharmacyGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pharmacy_grade_generator")
    @SequenceGenerator(name="pharmacy_grade_generator", sequenceName = "pharmacy_grade_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Pharmacy pharmacy;

    @ManyToOne
    @JoinColumn
    private Patient patient;

    @Column
    private int grade;

    public PharmacyGrade() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
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