package app.controller.impl;


import app.dto.ComplaintDTO;
import app.model.complaint.Complaint;
import app.model.user.Patient;
import app.service.ComplaintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping(value="/save", consumes = "application/json")
    public ResponseEntity<Complaint> save(@RequestBody Complaint complaint) {
        return new ResponseEntity<>(complaintService.save(complaint), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<ComplaintDTO>> getComplaints() {
        return new ResponseEntity<>(complaintService.getComplaints(), HttpStatus.OK);
        /*
        ArrayList<ComplaintDTO> complaintDTOS=new ArrayList<>();

        for(Complaint complaint :(List<Complaint>) complaintService.read()){
            complaintDTOS.add(new ComplaintDTO(complaint));
        }

        return new ResponseEntity<>(complaintDTOS, HttpStatus.OK);

         */
    }
}
