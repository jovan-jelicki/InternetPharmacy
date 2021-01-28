package app.dto;

import app.model.time.WorkingHours;
import app.model.user.Contact;
import app.model.user.Pharmacist;
import app.model.user.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class PharmacistDTO {
    @Id
    private Long id;
    private WorkingHours workingHours;
    private String firstName;
    private String lastName;
    private Contact contact;
    private UserType userType;

    public PharmacistDTO(Pharmacist pharmacist) {
        this.id = pharmacist.getId();
        this.firstName = pharmacist.getFirstName();
        this.lastName = pharmacist.getLastName();
        this.contact = pharmacist.getContact();
        this.userType = pharmacist.getUserType();

        this.workingHours = pharmacist.getWorkingHours();
        workingHours.getPharmacy().setMedicationReservation(null);

    }
}
