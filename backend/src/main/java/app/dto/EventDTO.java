package app.dto;

import app.model.appointment.Appointment;

import java.time.LocalDateTime;

//This class is used for Pharmacist and Dermatologist working hours calendar
public class EventDTO {
    private Long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private String desc;

    public EventDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.start = appointment.getPeriod().getPeriodStart();
        this.end = appointment.getPeriod().getPeriodEnd();
        createEventBasedOnAppointment(appointment);
    }

    private void createEventBasedOnAppointment(Appointment appointment) {
        if(appointment.getPatient() == null){
            this.title = "Free appointment!";
            this.desc = "Pharmacy " + appointment.getPharmacy().getName();
        }else {
            this.title = "Scheduled appointment!";
            this.desc = "Pharmacy " + appointment.getPharmacy().getName() + " \n"
                    + "Patient "  + appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName();
        }
    }

    public EventDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
