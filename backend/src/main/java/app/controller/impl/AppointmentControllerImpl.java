package app.controller.impl;

import app.model.appointment.Appointment;
import app.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping(value = "api/appointment")
public class AppointmentControllerImpl {
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentControllerImpl(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<Collection<Appointment>> read() {
        Collection<Appointment> appointments= appointmentService.read();
        Appointment appointment = (Appointment) appointments.toArray()[0];
        Long id = appointment.getPharmacy().getId();
        return new ResponseEntity<>(appointmentService.read(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Appointment>> read(@PathVariable Long id) {
        return new ResponseEntity<>(appointmentService.read(id), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Object> save(@RequestBody Appointment entity) {
        boolean ret = appointmentService.createAvailableAppointment(entity);
        if (ret)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
