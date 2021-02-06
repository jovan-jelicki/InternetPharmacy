package app.dto.grade;

import app.model.grade.GradeType;

import java.util.Objects;

public class AssetGradeDTO {
    private Long gradedId;
    private Long gradeId;
    private String name;
    private int grade;
    private GradeType type;

    public AssetGradeDTO(Long gradedId, String name, GradeType type) {
        this.gradedId = gradedId;
        this.name = name;
        this.type = type;
    }

    public AssetGradeDTO() {
    }

    public GradeType getType() {
        return type;
    }

    public void setType(GradeType type) {
        this.type = type;
    }

    public Long getGradedId() {
        return gradedId;
    }

    public void setGradedId(Long gradedId) {
        this.gradedId = gradedId;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        AssetGradeDTO that = (AssetGradeDTO) o;
        return Objects.equals(gradedId, that.gradedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gradedId);
    }
}
