package app.dto;

import app.model.user.EmployeeType;

public class PatientAppointmentsSearch {
    private Long patientId;
    private EmployeeType type;

    public PatientAppointmentsSearch() {
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }
}
