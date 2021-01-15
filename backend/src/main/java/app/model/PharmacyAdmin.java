package app.model;

import javax.persistence.*;

@Entity
public class PharmacyAdmin extends User {

    @JoinColumn
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