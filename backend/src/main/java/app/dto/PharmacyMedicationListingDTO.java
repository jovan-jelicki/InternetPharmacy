package app.dto;

import app.model.medication.Ingredient;
import app.model.medication.Medication;
import app.model.medication.MedicationQuantity;
import app.model.medication.MedicationType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PharmacyMedicationListingDTO {
    private String name;
    private int quantity;
    private double price;
    private MedicationType type;
    private Set<Ingredient> ingredients;
    private Set<Medication> alternatives;
    private int grade;

    public PharmacyMedicationListingDTO() {
    }

    public PharmacyMedicationListingDTO(MedicationQuantity medicationQuantity, double price, int grade) {
        this.name = medicationQuantity.getMedication().getName();
        this.quantity = medicationQuantity.getQuantity();
        this.price = price;
        this.type = medicationQuantity.getMedication().getType();
        this.ingredients =  medicationQuantity.getMedication().getIngredient();
        this.alternatives = medicationQuantity.getMedication().getAlternatives();
        this.grade = grade;
    }
}
