package app.service;

import app.dto.ComplaintDTO;
import app.model.complaint.Complaint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

public interface ComplaintService extends CRUDService<Complaint>{
    Collection<ComplaintDTO> getComplaints();
    Boolean editComplaint( Long complaintId);

    }
