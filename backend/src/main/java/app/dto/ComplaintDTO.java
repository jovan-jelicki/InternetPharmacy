package app.dto;

import app.model.complaint.Complaint;
import app.model.complaint.ComplaintType;

public class ComplaintDTO {
    private Long complaintId;
    private Long complainee_id;
    private String name;
    private ComplaintType type;
    private Boolean isActive;
    private String content;
    private Long patientId;
    private String patientFullName;
    private String patientEmail;

    public ComplaintDTO(){}

    public ComplaintDTO(Complaint complaint){
        this.complaintId=complaint.getId();
        this.complainee_id = complaint.getComplaineeId();
        this.type = complaint.getType();
        this.content = complaint.getContent();
        this.patientId = complaint.getPatient().getId();
        this.patientEmail = complaint.getPatient().getCredentials().getEmail();
        this.isActive = complaint.getActive();
    }
    public ComplaintDTO(Long complaintId, Long complainee_id, String name, ComplaintType type, Boolean isActive, String content, Long patientId, String patientFullName, String patientEmail) {
        this.complaintId = complaintId;
        this.complainee_id = complainee_id;
        this.name = name;
        this.type = type;
        this.isActive = isActive;
        this.content = content;
        this.patientId = patientId;
        this.patientFullName = patientFullName;
        this.patientEmail = patientEmail;
    }

    public String getPatientFullName() {
        return patientFullName;
    }

    public void setPatientFullName(String patientFullName) {
        this.patientFullName = patientFullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getComplainee_id() {
        return complainee_id;
    }

    public void setComplainee_id(Long complainee_id) {
        this.complainee_id = complainee_id;
    }

    public ComplaintType getType() {
        return type;
    }

    public void setType(ComplaintType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public Boolean getActive() {return isActive;}

    public void setActive(Boolean active) {isActive = active;}

    public Long getComplaintId() {return complaintId; }

    public void setComplaintId(Long complaintId) {this.complaintId = complaintId;}
}