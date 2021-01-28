package app.dto;

import app.model.medication.MedicationQuantity;
import app.model.medication.MedicationReservation;
import app.model.medication.MedicationReservationStatus;
import app.model.user.Patient;

import java.time.LocalDateTime;

public class MedicationReservationSimpleInfoDTO {
    private Long id;
    private Patient patient = new Patient();
    private MedicationQuantity medicationQuantity;
    private LocalDateTime pickUpDate;
    private MedicationReservationStatus status;


    public MedicationReservationSimpleInfoDTO(MedicationReservation medicationReservation) {
        this.id = medicationReservation.getId();
        this.patient.setId(medicationReservation.getPatient().getId());
        this.patient.setFirstName(medicationReservation.getPatient().getFirstName());
        this.patient.setLastName(medicationReservation.getPatient().getLastName());
        this.medicationQuantity = medicationReservation.getMedicationQuantity();
        this.pickUpDate = medicationReservation.getPickUpDate();
        this.status = medicationReservation.getStatus();
    }

    public MedicationReservationSimpleInfoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public MedicationQuantity getMedicationQuantity() {
        return medicationQuantity;
    }

    public void setMedicationQuantity(MedicationQuantity medicationQuantity) {
        this.medicationQuantity = medicationQuantity;
    }

    public LocalDateTime getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDateTime pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public MedicationReservationStatus getStatus() {
        return status;
    }

    public void setStatus(MedicationReservationStatus status) {
        this.status = status;
    }
}
