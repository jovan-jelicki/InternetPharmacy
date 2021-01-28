
package app.model.medication;

import app.model.user.Patient;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class EPrescription {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prescription_generator")
    @SequenceGenerator(name="prescription_generator", sequenceName = "prescription_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    @Column
    private LocalDateTime dateIssued;

    @ManyToOne
    @JoinColumn
    private Patient patient;

    @ManyToMany
    private List<MedicationQuantity> medicationQuantity;

    public EPrescription() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDateTime dateIssued) {
        this.dateIssued = dateIssued;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<MedicationQuantity> getMedicationQuantity() {
        return medicationQuantity;
    }

    public void setMedicationQuantity(List<MedicationQuantity> medicationQuantity) {
        this.medicationQuantity = medicationQuantity;
    }
}