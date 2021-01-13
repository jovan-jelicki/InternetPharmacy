package app.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

public class Supplier extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private List<MedicationQuantity> medicationQuantity;

    @ManyToMany
    private List<MedicationOffer> medicationOffer;

    public Supplier() {
    }

    @Override
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