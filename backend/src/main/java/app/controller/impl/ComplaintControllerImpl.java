package app.controller.impl;


import app.dto.ComplaintDTO;
import app.model.complaint.Complaint;
import app.model.user.Patient;
import app.service.ComplaintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value = "api/complaints")
public class ComplaintControllerImpl {
    private final ComplaintService complaintService;

    public ComplaintControllerImpl(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PreAuthorize("hasAnyRole('systemAdmin','patient')")
    @PostMapping(value="/save", consumes = "application/json")
    public ResponseEntity<Void> save(@RequestBody Complaint complaint) {
        complaintService.save(complaint);
       // return new ResponseEntity<>(complaintService.save(complaint), HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @PreAuthorize("hasRole('systemAdmin')")
    @GetMapping
    public ResponseEntity<Collection<ComplaintDTO>> getComplaints() {
        return new ResponseEntity<>(complaintService.getComplaints(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('systemAdmin')")
    @GetMapping(value = "/edit/{complaintId}")
    public ResponseEntity<Boolean> editComplaint(@RequestBody Long complaintId) {
        return new ResponseEntity<>(complaintService.editComplaint(complaintId), HttpStatus.OK);

    }
}
