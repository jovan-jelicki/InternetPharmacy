package app.dto;

import app.model.medication.MedicationOrder;
import app.model.medication.MedicationOrderStatus;
import app.model.medication.MedicationQuantity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MedicationOrderDTO {
    private Long id;
    private String pharmacyAdminFirstName;
    private String pharmacyAdminLastName;
    private Long pharmacyAdminId;
    private LocalDateTime deadline;
    private List<MedicationQuantity> medicationQuantity;
    private MedicationOrderStatus status;
    private Long medicationOrderVersion;

    public MedicationOrderDTO() {
    }

    public MedicationOrderDTO(MedicationOrder medicationOrder) {
        this.id = medicationOrder.getId();
        this.pharmacyAdminFirstName = medicationOrder.getPharmacyAdmin().getFirstName();
        this.pharmacyAdminLastName = medicationOrder.getPharmacyAdmin().getLastName();
        this.pharmacyAdminId = medicationOrder.getPharmacyAdmin().getId();
        this.deadline = medicationOrder.getDeadline();
        this.medicationQuantity = medicationOrder.getMedicationQuantity();
        this.status = medicationOrder.getStatus();
        this.medicationOrderVersion = medicationOrder.getVersion();
    }
}
