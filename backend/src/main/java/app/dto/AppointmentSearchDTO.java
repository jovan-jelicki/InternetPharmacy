package app.dto;

import app.model.user.EmployeeType;

import java.time.LocalDateTime;

public class AppointmentSearchDTO {
    private LocalDateTime timeSlot;
    private EmployeeType employeeType;

    public AppointmentSearchDTO() {
    }

    public LocalDateTime getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(LocalDateTime timeSlot) {
        this.timeSlot = timeSlot;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }
}
