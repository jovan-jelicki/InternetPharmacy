package app.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Pharmacist extends User {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WorkingHours> workingHours;

    public Pharmacist() {
    }

    public List<WorkingHours> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHours> workingHours) {
        this.workingHours = workingHours;
    }
}