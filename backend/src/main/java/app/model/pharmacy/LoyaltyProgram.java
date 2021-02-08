package app.model.pharmacy;

import javax.persistence.*;

@Entity
public class LoyaltyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loyalty_program_generator")
    @SequenceGenerator(name="loyalty_program_generator", sequenceName = "loyalty_program_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    @Column(nullable = false)
    private int appointmentPoints;

    @Column(nullable = false)
    private int consultingPoints;

    @Version
    @Column(nullable = false, columnDefinition = "int default 1")
    private Long version;

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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}