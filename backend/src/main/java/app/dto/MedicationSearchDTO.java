package app.dto;

public class MedicationSearchDTO {
    private String name;

    public MedicationSearchDTO() {
    }

    public MedicationSearchDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
