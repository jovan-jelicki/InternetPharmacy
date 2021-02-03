package app.model.pharmacy;

import app.model.time.Period;

import javax.persistence.*;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promotion_generator")
    @SequenceGenerator(name="promotion_generator", sequenceName = "promotion_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    private Period period;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn
    private Pharmacy pharmacy;

    public Promotion() { }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}