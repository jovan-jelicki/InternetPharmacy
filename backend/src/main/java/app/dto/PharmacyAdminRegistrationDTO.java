package app.dto;

import app.model.pharmacy.Pharmacy;
import app.model.user.Contact;
import app.model.user.Credentials;
import app.model.user.UserType;

public class PharmacyAdminRegistrationDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private UserType userType;
    private Credentials credentials;
    private Contact contact;
    private boolean approvedAccount;
    private Long pharmacyId;

    public PharmacyAdminRegistrationDTO(Long id, String firstName, String lastName, UserType userType, Credentials credentials, Contact contact, boolean approvedAccount, Long pharmacyId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.credentials = credentials;
        this.contact = contact;
        this.approvedAccount = approvedAccount;
        this.pharmacyId = pharmacyId;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public boolean isApprovedAccount() {
        return approvedAccount;
    }

    public void setApprovedAccount(boolean approvedAccount) {
        this.approvedAccount = approvedAccount;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }
}
