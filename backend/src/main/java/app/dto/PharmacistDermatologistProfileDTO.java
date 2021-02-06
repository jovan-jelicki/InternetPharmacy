package app.dto;

import app.model.user.*;

public class PharmacistDermatologistProfileDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserType userType;
    private Contact contact;



    public PharmacistDermatologistProfileDTO(Dermatologist user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getCredentials().getEmail();
        this.userType = user.getUserType();
        this.contact = user.getContact();
    }

    public PharmacistDermatologistProfileDTO() {
    }

    public PharmacistDermatologistProfileDTO(Pharmacist user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getCredentials().getEmail();
        this.userType = user.getUserType();
        this.contact = user.getContact();
    }

    public Dermatologist convertDtoToDermatologist(Dermatologist dermatologist){
        dermatologist.setFirstName(this.firstName);
        dermatologist.setLastName(this.lastName);
        dermatologist.setContact(this.contact);
        return dermatologist;
    }


    public Pharmacist convertDtoToPharmacist(Pharmacist pharmacist){
        pharmacist.setFirstName(this.firstName);
        pharmacist.setLastName(this.lastName);
        pharmacist.setContact(this.contact);
        return pharmacist;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
