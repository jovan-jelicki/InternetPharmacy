package app.controller.impl;

import app.dto.AppointmentListingDTO;
import app.dto.AppointmentScheduledDTO;
import app.dto.EventDTO;
import app.dto.ExaminerDTO;
import app.model.appointment.Appointment;
import app.service.AppointmentService;
import app.service.DermatologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping(value = "api/appointment")
public class AppointmentControllerImpl {
    private final AppointmentService appointmentService;
    private final DermatologistService dermatologistService;

    @Autowired
    public AppointmentControllerImpl(AppointmentService appointmentService, DermatologistService dermatologistService) {
        this.appointmentService = appointmentService;
        this.dermatologistService = dermatologistService;
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

    @PostMapping(value = "/counseling")
    public ResponseEntity<Void> scheduleCounseling(@RequestBody Appointment entity) {
        if(appointmentService.scheduleCounseling(entity) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/getEvents")
    public ResponseEntity<Collection<EventDTO>> getEventsByExaminer(@RequestBody ExaminerDTO examinerDTO){
        return new ResponseEntity<>(appointmentService.getAllEventsOfExaminer(examinerDTO.getId(), examinerDTO.getType()), HttpStatus.OK);
    }

    @PostMapping(value = "/getAllScheduledByExaminer")
    public ResponseEntity<Collection<AppointmentScheduledDTO>> getScheduledAppointmentsByExaminer(@RequestBody ExaminerDTO examinerDTO){
        return new ResponseEntity<>(appointmentService.getAllAppointmentsByExaminer(examinerDTO.getId(), examinerDTO.getType()), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllAvailableAppointmentsByPharmacy/{id}")
    public ResponseEntity<Collection<AppointmentListingDTO>> getAllAvailableAppointmentsByPharmacy(@PathVariable Long id){
        ArrayList<AppointmentListingDTO> appointmentListingDTOS = new ArrayList<>();
        for (Appointment appointment : appointmentService.GetAllAvailableAppointmentsByPharmacy(id)) {
            AppointmentListingDTO appointmentListingDTO = new AppointmentListingDTO(appointment);
            appointmentListingDTO.setDermatologistFirstName(dermatologistService.read(appointment.getExaminerId()).get().getFirstName());
            appointmentListingDTO.setDermatologistLastName(dermatologistService.read(appointment.getExaminerId()).get().getLastName());
            appointmentListingDTOS.add(appointmentListingDTO);
        }
        return new ResponseEntity<>(appointmentListingDTOS, HttpStatus.OK);
    }
}
