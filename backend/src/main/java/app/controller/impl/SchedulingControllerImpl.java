package app.controller.impl;

import app.dto.AppointmentSearchDTO;
import app.model.user.EmployeeType;
import app.model.user.Pharmacist;
import app.service.CounselingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "api/scheduling")
public class SchedulingControllerImpl {
    private CounselingService counselingService;

    @Autowired
    public SchedulingControllerImpl(CounselingService counselingService) {
        this.counselingService = counselingService;
    }

    @PostMapping(value = "/search", consumes = "application/json")
    public ResponseEntity<Collection<Pharmacist>> getAvailable(@RequestBody AppointmentSearchDTO appointmentSearchKit) {
        if (appointmentSearchKit.getEmployeeType() == EmployeeType.pharmacist)
            return new ResponseEntity<>(counselingService.findAvailablePharmacists(appointmentSearchKit.getTimeSlot()),
                    HttpStatus.OK);
        else
            // ZA DERMATOLOGE KASNIJE DODATI
            return new ResponseEntity<>(counselingService.findAvailablePharmacists(appointmentSearchKit.getTimeSlot()),
                HttpStatus.OK);
    }
}
