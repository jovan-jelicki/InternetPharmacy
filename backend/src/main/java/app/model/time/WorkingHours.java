package app.model.time;

import app.model.pharmacy.Pharmacy;

import javax.persistence.*;

@Entity
public class WorkingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "working_hours_generator")
    @SequenceGenerator(name="working_hours_generator", sequenceName = "working_hours_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    private Period period;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private Pharmacy pharmacy;

    public WorkingHours() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}