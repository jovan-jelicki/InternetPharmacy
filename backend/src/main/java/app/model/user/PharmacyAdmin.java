package app.model.user;

import app.model.pharmacy.Pharmacy;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;

@Entity
public class PharmacyAdmin extends User {

    @JoinColumn
    @LazyToOne(LazyToOneOption.FALSE)
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Pharmacy pharmacy;

    public PharmacyAdmin() {
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}