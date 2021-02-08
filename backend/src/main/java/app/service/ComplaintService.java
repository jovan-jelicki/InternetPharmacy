package app.service;

import app.dto.ComplaintDTO;
import app.model.complaint.Complaint;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface ComplaintService extends CRUDService<Complaint>{
    Collection<ComplaintDTO> getComplaints();

    }
