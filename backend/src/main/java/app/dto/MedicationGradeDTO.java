package app.dto;

import app.model.medication.Medication;
import app.model.medication.MedicationType;

public class MedicationGradeDTO {
    private Medication medication;
    private double grade;

    public MedicationGradeDTO(){

    }
    public MedicationGradeDTO(Medication medication, double grade) {
        this.medication = medication;
        this.grade = grade;
    }

    public MedicationGradeDTO(Medication medication) {
        this.medication = medication;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
