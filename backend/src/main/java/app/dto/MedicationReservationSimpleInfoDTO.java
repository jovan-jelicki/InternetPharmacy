package app.dto;

import app.model.medication.MedicationQuantity;
import app.model.medication.MedicationReservation;
import app.model.medication.MedicationReservationStatus;

import java.time.LocalDateTime;

public class MedicationReservationSimpleInfoDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private MedicationQuantity medicationQuantity;
    private LocalDateTime pickUpDate;
    private MedicationReservationStatus status;


    public MedicationReservationSimpleInfoDTO(MedicationReservation medicationReservation) {
        this.id = medicationReservation.getId();
        this.firstName = medicationReservation.getPatient().getFirstName();
        this.lastName = medicationReservation.getPatient().getLastName();
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
