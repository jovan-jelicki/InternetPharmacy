package app.model.user;

import app.model.pharmacy.Pharmacy;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;

@Entity
public class PharmacyAdmin extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pharmacy_admin_generator")
    @SequenceGenerator(name="pharmacy_admin_generator", sequenceName = "pharmacy_admin_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    @JoinColumn
    @LazyToOne(LazyToOneOption.FALSE)
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Pharmacy pharmacy;

    public PharmacyAdmin() {
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