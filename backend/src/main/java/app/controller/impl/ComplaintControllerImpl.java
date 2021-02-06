package app.controller.impl;


import app.model.complaint.Complaint;
import app.model.user.Patient;
import app.service.ComplaintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "api/complaints")
public class ComplaintControllerImpl {
    private final ComplaintService complaintService;

    public ComplaintControllerImpl(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping(value="/save", consumes = "application/json")
    public ResponseEntity<Complaint> save(@RequestBody Complaint complaint) {
        return new ResponseEntity<>(complaintService.save(complaint), HttpStatus.CREATED);
    }
}
