package app.dto;

import app.model.user.EmployeeType;

public class ExaminerDTO {
    private Long id;
    private EmployeeType type;

    public ExaminerDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }
}
