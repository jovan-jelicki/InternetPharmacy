package app.dto;

import app.model.medication.EPrescription;
import app.model.medication.Medication;
import app.model.medication.MedicationQuantity;
import app.model.time.Period;

import java.time.LocalDateTime;
import java.util.Collection;

public class EPrescriptionSimpleInfoDTO {
    private Long id;
    private Collection<MedicationQuantity>  medicationQuantity;
    private LocalDateTime dateIssued;

    public EPrescriptionSimpleInfoDTO(EPrescription ePrescription) {
        this.id = ePrescription.getId();
        this.medicationQuantity = ePrescription.getMedicationQuantity();
        this.dateIssued = ePrescription.getDateIssued();
    }

    public EPrescriptionSimpleInfoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<MedicationQuantity> getMedicationQuantity() {
        return medicationQuantity;
    }

    public void setMedicationQuantity(Collection<MedicationQuantity> medicationQuantity) {
        this.medicationQuantity = medicationQuantity;
    }

    public LocalDateTime getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDateTime dateIssued) {
        this.dateIssued = dateIssued;
    }
}
