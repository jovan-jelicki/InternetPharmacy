package app.dto;

import app.model.medication.Ingredient;
import app.model.pharmacy.Promotion;
import app.model.user.Contact;
import app.model.user.Patient;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

public class PatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Contact contact;
    private List<Ingredient> allergies;
    private int penaltyCount;

    public PatientDTO() {}

    public PatientDTO (Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.email = patient.getCredentials().getEmail();
        this.contact = patient.getContact();
        this.allergies = patient.getAllergies();
        this.penaltyCount = patient.getPenaltyCount();
    }

    public void merge(Patient patient) {
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setContact(contact);
        patient.setAllergies(allergies);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Ingredient> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Ingredient> allergies) {
        this.allergies = allergies;
    }

    public int getPenaltyCount() {
        return penaltyCount;
    }

    public void setPenaltyCount(int penaltyCount) {
        this.penaltyCount = penaltyCount;
    }
}
