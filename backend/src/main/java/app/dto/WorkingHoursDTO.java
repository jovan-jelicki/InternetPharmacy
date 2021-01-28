package app.dto;

import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.model.time.WorkingHours;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class WorkingHoursDTO {

    @Id
    private Long id;
    private Period period;
    private Pharmacy pharmacy;

    public WorkingHoursDTO(WorkingHours workingHours) {
        this.id = workingHours.getId();
        this.period = workingHours.getPeriod();
        this.pharmacy = workingHours.getPharmacy();

        this.pharmacy.setMedicationReservation(null);
        this.pharmacy.setMedicationQuantity(null);
    }
}
