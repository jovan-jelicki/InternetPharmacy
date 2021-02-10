package app.model.pharmacy;


import app.dto.PharmacySearchDTO;
import app.model.medication.EPrescription;
import app.model.medication.MedicationQuantity;
import app.model.medication.MedicationReservation;
import app.model.user.Address;

import javax.persistence.*;
import java.util.List;

@Entity
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pharmacy_generator")
    @SequenceGenerator(name="pharmacy_generator", sequenceName = "pharmacy_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    @Column
    private String name;

    private Address address;

    @Column(columnDefinition = "TEXT")
    private String description;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MedicationQuantity> medicationQuantity;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MedicationReservation> medicationReservation;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EPrescription> prescriptions;

    @Column
    private int pharmacistCost;

    @Column
    private int dermatologistCost;

    @Version
    @Column(nullable = false, columnDefinition = "int default 1")
    private Long version;

    public Pharmacy() {
    }

    public List<EPrescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<EPrescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void setDermatologistCost(int dermatologistCost) {
        this.dermatologistCost = dermatologistCost;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getPharmacistCost() {
        return pharmacistCost;
    }

    public void setPharmacistCost(int pharmacistCost) {
        this.pharmacistCost = pharmacistCost;
    }

    public int getDermatologistCost() {
        return dermatologistCost;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isEqual(PharmacySearchDTO pharmacySearchDTO) {
        return searchCondition(pharmacySearchDTO.getName(), name)
                && searchCondition(pharmacySearchDTO.getCountry(), address.getCountry())
                && searchCondition(pharmacySearchDTO.getTown(), address.getTown())
                && searchCondition(pharmacySearchDTO.getStreet(), address.getStreet());
    }

    private boolean searchCondition(String searched, String actual) {
        if(searched.trim().isEmpty())
            return true;
        else
            return actual.toLowerCase().contains(searched.toLowerCase());
    }


}