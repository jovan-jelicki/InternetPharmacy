package app.model;

import javax.persistence.*;

@Entity
public class LoyaltyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int appointmentPoints;

    @Column(nullable = false)
    private int consultingPoints;

    public LoyaltyProgram() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAppointmentPoints() {
        return appointmentPoints;
    }

    public void setAppointmentPoints(int appointmentPoints) {
        this.appointmentPoints = appointmentPoints;
    }

    public int getConsultingPoints() {
        return consultingPoints;
    }

    public void setConsultingPoints(int consultingPoints) {
        this.consultingPoints = consultingPoints;
    }
}