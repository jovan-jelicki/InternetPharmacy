package app.dto;

import app.model.medication.Medication;
import app.model.medication.MedicationLackingEvent;
import app.model.user.EmployeeType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MedicationLackingEventDTO {
    private Long id;
    private Long employeeId;
    private EmployeeType employeeType;
    private Medication medication;
    private LocalDateTime eventDate;
    private String employeeFirstName;
    private String employeeLastName;
    private Long pharmacyId;


    public MedicationLackingEventDTO() {
    }

    public MedicationLackingEventDTO(MedicationLackingEvent medicationLackingEvent) {
        this.id = medicationLackingEvent.getId();
        this.employeeId = medicationLackingEvent.getId();
        this.employeeType = medicationLackingEvent.getEmployeeType();
        this.medication = medicationLackingEvent.getMedication();
        this.eventDate = medicationLackingEvent.getEventDate();
        this.pharmacyId = medicationLackingEvent.getPharmacyId();
    }

}
