package app.dto;

import app.model.time.WorkingHours;
import app.model.user.Contact;
import app.model.user.Dermatologist;
import app.model.user.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
public class DermatologistDTO {
    @Id
    private Long id;
    private List<WorkingHours> workingHours;
    private String firstName;
    private String lastName;
    private Contact contact;
    private UserType userType;

    public DermatologistDTO(Dermatologist dermatologist) {
        this.id = dermatologist.getId();
        this.firstName = dermatologist.getFirstName();
        this.lastName = dermatologist.getLastName();
        this.contact = dermatologist.getContact();
        this.userType = dermatologist.getUserType();
        this.workingHours = dermatologist.getWorkingHours();

        for (WorkingHours workingHours : this.workingHours)
            workingHours.getPharmacy().setMedicationReservation(null);

    }
}
