package app.model;


import javax.persistence.*;
import java.util.List;

@Entity
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String description;

    @ManyToMany
    private List<Dermatologist> dermatologist;

    @OneToMany
    private List<Pharmacist> pharmacist;

    @ManyToMany
    private List<MedicationQuantity> medicationQuantity;

    @ManyToMany
    private List<MedicationReservation> medicationReservation;

    public Pharmacy() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Dermatologist> getDermatologist() {
        return dermatologist;
    }

    public void setDermatologist(List<Dermatologist> dermatologist) {
        this.dermatologist = dermatologist;
    }

    public List<Pharmacist> getPharmacist() {
        return pharmacist;
    }

    public void setPharmacist(List<Pharmacist> pharmacist) {
        this.pharmacist = pharmacist;
    }

    public List<MedicationQuantity> getMedicationQuantity() {
        return medicationQuantity;
    }

    public void setMedicationQuantity(List<MedicationQuantity> medicationQuantity) {
        this.medicationQuantity = medicationQuantity;
    }

    public List<MedicationReservation> getMedicationReservation() {
        return medicationReservation;
    }

    public void setMedicationReservation(List<MedicationReservation> medicationReservation) {
        this.medicationReservation = medicationReservation;
    }
}