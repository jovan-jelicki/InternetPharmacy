package app.model;

import javax.persistence.*;

@Entity
public class Pharmacist extends User {

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private WorkingHours workingHours;

    public Pharmacist() {
    }

    public WorkingHours getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(WorkingHours workingHours) {
        this.workingHours = workingHours;
    }
}