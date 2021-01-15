package app.dto;


import app.model.Address;
import app.model.PharmacyAdmin;
import app.model.SystemAdmin;
import app.model.UserType;

public class PharmacyAdminDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Address address;
    private String town;
    private String country;
    private String phoneNumber;
    private UserType userType;
    private Long pharmacyId;

    public PharmacyAdminDTO() {
    }

    public PharmacyAdminDTO(PharmacyAdmin pharmacyAdmin) {
        this.id = pharmacyAdmin.getId();
        this.firstName = pharmacyAdmin.getFirstName();
        this.lastName = pharmacyAdmin.getLastName();
        this.address = pharmacyAdmin.getContact().getAddress();
        this.phoneNumber = pharmacyAdmin.getContact().getPhoneNumber();
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}
