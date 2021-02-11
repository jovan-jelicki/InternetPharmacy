package app.controller.impl;


import app.dto.ComplaintDTO;
import app.model.complaint.Complaint;
import app.service.ComplaintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping(value = "api/complaints")
public class ComplaintControllerImpl {
    private final ComplaintService complaintService;

    public ComplaintControllerImpl(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PreAuthorize("hasAnyRole('patient,systemAdmin')")
    @PostMapping(value="/save", consumes = "application/json")
    public ResponseEntity<Void> save(@RequestBody Complaint complaint) {
        try {
            complaintService.save(complaint);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @PreAuthorize("hasRole('systemAdmin')")
    @GetMapping
    public ResponseEntity<Collection<ComplaintDTO>> getComplaints() {
        return new ResponseEntity<>(complaintService.getComplaints(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('systemAdmin')")
    @GetMapping(value = "/edit/{complaintId}")
    public ResponseEntity<Boolean> editComplaint(@PathVariable Long complaintId) {
        return new ResponseEntity<>(complaintService.editComplaint(complaintId), HttpStatus.OK);

    }
}
