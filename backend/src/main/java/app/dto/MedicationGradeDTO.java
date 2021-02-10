package app.dto;

import app.model.medication.*;

import javax.persistence.*;
import java.util.Set;

public class MedicationGradeDTO {
    private Long id;
    private String name;
    private MedicationType type;
    private double dose;
    private int loyaltyPoints;
    private MedicationShape medicationShape;
    private String manufacturer;
    private MedicationIssue medicationIssue;
    private String note;
    private Set<Ingredient> ingredient;
    private Set<SideEffect> sideEffect;
    private Set<Medication> alternatives;
    private double grade;

    public MedicationGradeDTO(){

    }
    public MedicationGradeDTO(Medication medication, double grade) {
        this.id = medication.getId();
        this.name=medication.getName();
        this.type=medication.getType();
        this.dose=medication.getDose();
        this.loyaltyPoints=medication.getLoyaltyPoints();
        this.medicationShape=medication.getMedicationShape();
        this.manufacturer=medication.getManufacturer();
        this.medicationIssue=medication.getMedicationIssue();
        this.note=medication.getNote();
        this.ingredient=medication.getIngredient();
        this.sideEffect=medication.getSideEffect();
        this.alternatives=medication.getAlternatives();
        this.grade = grade;
    }

    public MedicationGradeDTO(Medication medication) {
        this.id = medication.getId();
        this.name=medication.getName();
        this.type=medication.getType();
        this.dose=medication.getDose();
        this.loyaltyPoints=medication.getLoyaltyPoints();
        this.medicationShape=medication.getMedicationShape();
        this.manufacturer=medication.getManufacturer();
        this.medicationIssue=medication.getMedicationIssue();
        this.note=medication.getNote();
        this.ingredient=medication.getIngredient();
        this.sideEffect=medication.getSideEffect();
        this.alternatives=medication.getAlternatives();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MedicationType getType() {
        return type;
    }

    public void setType(MedicationType type) {
        this.type = type;
    }

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public MedicationShape getMedicationShape() {
        return medicationShape;
    }

    public void setMedicationShape(MedicationShape medicationShape) {
        this.medicationShape = medicationShape;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public MedicationIssue getMedicationIssue() {
        return medicationIssue;
    }

    public void setMedicationIssue(MedicationIssue medicationIssue) {
        this.medicationIssue = medicationIssue;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<Ingredient> getIngredient() {
        return ingredient;
    }

    public void setIngredient(Set<Ingredient> ingredient) {
        this.ingredient = ingredient;
    }

    public Set<SideEffect> getSideEffect() {
        return sideEffect;
    }

    public void setSideEffect(Set<SideEffect> sideEffect) {
        this.sideEffect = sideEffect;
    }

    public Set<Medication> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(Set<Medication> alternatives) {
        this.alternatives = alternatives;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
