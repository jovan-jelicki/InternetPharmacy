package app.dto;

import app.model.user.Contact;
import app.model.user.Pharmacist;
import app.model.user.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PharmacistPlainDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Contact contact;
    private UserType userType;

    public PharmacistPlainDTO(Pharmacist pharmacist) {
        this.id = pharmacist.getId();
        this.firstName = pharmacist.getFirstName();
        this.lastName = pharmacist.getLastName();
        this.contact = pharmacist.getContact();
        this.userType = pharmacist.getUserType();
    }

    public PharmacistPlainDTO() {}
}
