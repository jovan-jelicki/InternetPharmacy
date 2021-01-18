package app.dto;


import app.model.user.Contact;
import app.model.user.PharmacyAdmin;
import app.model.user.UserType;

public class PharmacyAdminDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Contact contact;
    private UserType userType;
    private Long pharmacyId;

    public PharmacyAdminDTO(PharmacyAdmin pharmacyAdmin) {
        this.id = pharmacyAdmin.getId();
        this.email = pharmacyAdmin.getCredentials().getEmail();;
        this.firstName = pharmacyAdmin.getFirstName();
        this.lastName = pharmacyAdmin.getLastName();
        this.contact = pharmacyAdmin.getContact();
        this.userType = pharmacyAdmin.getUserType();
        this.pharmacyId = pharmacyAdmin.getPharmacy().getId();
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

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
