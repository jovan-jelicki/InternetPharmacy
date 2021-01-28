package app.model.appointment;

import app.model.medication.Medication;
import app.model.time.Period;

import javax.persistence.*;

@Entity
public class Therapy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "therapy_generator")
    @SequenceGenerator(name="therapy_generator", sequenceName = "therapy_seq", allocationSize=50, initialValue = 1000)
    private long id;

    @ManyToOne
    @JoinColumn
    private Medication medication;

    private Period period;

    public Therapy() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}