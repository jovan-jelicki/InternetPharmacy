
package app.model.user;

import app.model.medication.Ingredient;
import app.model.pharmacy.Promotion;

import javax.persistence.*;
import java.util.List;

@Entity
public class Patient extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_generator")
    @SequenceGenerator(name="patient_generator", sequenceName = "patient_seq", allocationSize=50, initialValue = 1000)
    private Long id;

    @Column
    private int penaltyCount;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ingredient> allergies;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Promotion> promotions;

    @Version
    @Column(nullable = false, columnDefinition = "int default 1")
    private Long version;

    public Patient() {
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPenaltyCount() {
        return penaltyCount;
    }

    public void setPenaltyCount(int penaltyCount) {
        this.penaltyCount = penaltyCount;
    }

    public List<Ingredient> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Ingredient> allergies) {
        this.allergies = allergies;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
}