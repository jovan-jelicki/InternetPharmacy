package app.model.medication;

import javax.persistence.*;

@Entity
public class MedicationQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_quantity_generator")
    @SequenceGenerator(name="medication_quantity_generator", sequenceName = "medication_quantity_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Medication medication;

    @Column
    private int quantity;

    public MedicationQuantity() {
    }

    public MedicationQuantity(Medication medication, int quantity) {
        this.medication = medication;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity = this.quantity + quantity;
    }

    public void subtractQuantity(int quantity) {
        if(this.quantity < quantity)
            throw new IllegalArgumentException("Not enough medicine");
        this.quantity = this.quantity - quantity;
    }
}