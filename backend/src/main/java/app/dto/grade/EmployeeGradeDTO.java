package app.dto.grade;

import app.model.grade.GradeType;

import java.util.Objects;

public class EmployeeGradeDTO {
    private Long employeeId;
    private Long gradeId;
    private String firstName;
    private String lastName;
    private GradeType type;
    private int grade;

    public EmployeeGradeDTO(Long employeeId, String firstName, String lastName, GradeType type) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GradeType getType() {
        return type;
    }

    public void setType(GradeType type) {
        this.type = type;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeGradeDTO that = (EmployeeGradeDTO) o;
        return Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }
}
