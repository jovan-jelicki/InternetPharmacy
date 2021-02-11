package app.model.user;

import app.model.medication.MedicationOffer;
import app.model.medication.MedicationQuantity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Supplier extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_generator")
    @SequenceGenerator(name="supplier_generator", sequenceName = "supplier_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MedicationQuantity> medicationQuantity;

    @OneToMany
    private List<MedicationOffer> medicationOffer;

    public Supplier() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MedicationQuantity> getMedicationQuantity() {
        return medicationQuantity;
    }

    public void setMedicationQuantity(List<MedicationQuantity> medicationQuantity) {
        this.medicationQuantity = medicationQuantity;
    }

    public List<MedicationOffer> getMedicationOffer() {
        return medicationOffer;
    }

    public void setMedicationOffer(List<MedicationOffer> medicationOffer) {
        this.medicationOffer = medicationOffer;
    }
}