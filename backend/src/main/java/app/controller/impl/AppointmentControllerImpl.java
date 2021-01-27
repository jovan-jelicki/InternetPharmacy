package app.controller.impl;

import app.model.appointment.Appointment;
import app.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "api/appointment")
public class AppointmentControllerImpl {
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentControllerImpl(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    @PostMapping(consumes = "application/json")
    public ResponseEntity<Appointment> save(@RequestBody Appointment entity) {
        return new ResponseEntity<>(appointmentService.save(entity), HttpStatus.CREATED);
    }

}
