
package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Patient extends User {

    @Column
    private int penaltyCount;

    @ManyToMany
    private List<Ingredient> allergies;

    @ManyToMany
    private List<Promotion> promotions;

    public Patient() {
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