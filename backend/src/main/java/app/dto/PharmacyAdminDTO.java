package app.dto;


import app.model.user.Contact;
import app.model.user.PharmacyAdmin;
import app.model.user.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PharmacyAdminDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Contact contact;
    private UserType userType;
    private Long pharmacyId;
    private String pharmacyName;

    public PharmacyAdminDTO(PharmacyAdmin pharmacyAdmin) {
        this.id = pharmacyAdmin.getId();
        this.email = pharmacyAdmin.getCredentials().getEmail();;
        this.firstName = pharmacyAdmin.getFirstName();
        this.lastName = pharmacyAdmin.getLastName();
        this.contact = pharmacyAdmin.getContact();
        this.userType = pharmacyAdmin.getUserType();
        this.pharmacyId = pharmacyAdmin.getPharmacy().getId();
        this.pharmacyName = pharmacyAdmin.getPharmacy().getName();
    }

}
