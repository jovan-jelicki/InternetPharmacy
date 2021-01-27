package app.model.user;

import app.model.time.WorkingHours;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pharmacist extends User {

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private WorkingHours workingHours;

    public Pharmacist() {
    }

    public WorkingHours getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(WorkingHours workingHours) {
        this.workingHours = workingHours;
    }

    public boolean isOverlapping(LocalDateTime timeSlot) {
        WorkingHours wh = getWorkingHours();
        if(wh.getPeriod().getPeriodStart().isBefore(timeSlot) && wh.getPeriod().getPeriodEnd().isAfter(timeSlot))
            return true;
        return false;
    }
}