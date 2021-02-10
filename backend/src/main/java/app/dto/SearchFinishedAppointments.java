package app.dto;

import app.model.user.EmployeeType;

public class SearchFinishedAppointments {
    private Long id;
    private EmployeeType type;
    private String query;

    public SearchFinishedAppointments() {
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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
