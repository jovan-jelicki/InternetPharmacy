package app.dto;

import app.model.SystemAdmin;
import app.model.UserType;

public class SystemAdminDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String town;
    private String country;
    private String phoneNumber;
    private UserType userType;

    public SystemAdminDTO() {
    }

    public SystemAdminDTO(SystemAdmin systemAdmin) {
        this.id = systemAdmin.getId();
        this.firstName = systemAdmin.getFirstName();
        this.lastName = systemAdmin.getLastName();
        this.address = systemAdmin.getContact().getAddress();
        this.town =systemAdmin.getContact().getTown();
        this.country = systemAdmin.getContact().getCountry();
        this.phoneNumber = systemAdmin.getContact().getPhoneNumber();
        this.userType = systemAdmin.getUserType();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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
}
