package app.model.medication;

import app.model.user.Dermatologist;
import app.model.user.EmployeeType;
import app.model.user.Pharmacist;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MedicationLackingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_lacking_generator")
    @SequenceGenerator(name="medication_lacking_generator", sequenceName = "medication_lacking_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    @Column
    private Long employeeId;

    @Enumerated(EnumType.ORDINAL)
    private EmployeeType employeeType;

    @ManyToOne(fetch = FetchType.EAGER)
    private Medication medication;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Long pharmacyId;

    public MedicationLackingEvent() {
    }

    public MedicationLackingEvent(Dermatologist dermatologist, Medication medication, LocalDateTime localDateTime, Long pharmacyId) {
        this.employeeId = dermatologist.getId();
        this.employeeType = EmployeeType.dermatologist;
        this.medication = medication;
        this.eventDate = localDateTime;
        this.pharmacyId = pharmacyId;
    }

    public MedicationLackingEvent(Pharmacist pharmacist, Medication medication, LocalDateTime localDateTime, Long pharmacyId) {
        this.employeeId = pharmacist.getId();
        this.employeeType = EmployeeType.dermatologist;
        this.medication = medication;
        this.eventDate = localDateTime;
        this.pharmacyId = pharmacyId;
    }
}
