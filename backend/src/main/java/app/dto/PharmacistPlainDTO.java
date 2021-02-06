package app.dto;

import app.model.user.Contact;
import app.model.user.Pharmacist;
import app.model.user.UserType;

public class PharmacistPlainDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Contact contact;
    private UserType userType;
    private double grade;

    public PharmacistPlainDTO(Pharmacist pharmacist) {
        this.id = pharmacist.getId();
        this.firstName = pharmacist.getFirstName();
        this.lastName = pharmacist.getLastName();
        this.contact = pharmacist.getContact();
        this.userType = pharmacist.getUserType();
    }

    public PharmacistPlainDTO() {}

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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
