package app.dto;

import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.time.Period;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//this dto is used for listing all available appointments for patient
public class AppointmentListingDTO {
    private Long id;
    private Long dermatologistId;
    private String dermatologistFirstName;
    private String dermatologistLastName;
    private String pharmacyName;
    private double cost;
    private Period period;
    private AppointmentStatus appointmentStatus;
    private double dermatologistGrade;

    public AppointmentListingDTO() {
    }

    public AppointmentListingDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.dermatologistId = appointment.getExaminerId();
        this.pharmacyName = appointment.getPharmacy().getName();
        this.cost = appointment.getPharmacy().getDermatologistCost();
        this.period = appointment.getPeriod();
        this.appointmentStatus = appointment.getAppointmentStatus();
        //TODO dermatologist grade
    }

    public void setDermatologistFirstName(String dermatologistFirstName) {
        this.dermatologistFirstName = dermatologistFirstName;
    }

    public void setDermatologistLastName(String dermatologistLastName) {
        this.dermatologistLastName = dermatologistLastName;
    }
}
