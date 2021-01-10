package app.model;


import java.util.List;

public class Pharmacy {
    private String name;
    private String address;
    private String description;

    private List<Dermatologist> dermatologist;
    private List<Pharmacist> pharmacist;
    private List<MedicationQuantity> medicationQuantity;
    private List<MedicationReservation> medicationReservation;


}