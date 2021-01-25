package app.controller.impl;

import app.dto.AppointmentSearchDTO;
import app.model.appointment.Appointment;
import app.model.user.EmployeeType;
import app.service.AppointmentService;
import app.service.impl.CounselingSchedulingStrategy;
import app.service.impl.ExaminationSchedulingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping(value = "api/appointments")
public class AppointmentControllerImpl {
    AppointmentService appointmentService;

    @Autowired
    public AppointmentControllerImpl(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping(value = "/search", consumes = "application/json")
    public ResponseEntity<Collection<Appointment>> getAvailable(@RequestBody AppointmentSearchDTO appointmentSearchKit) {
        if(appointmentSearchKit.getEmployeeType() == EmployeeType.pharmacist)
            appointmentService.setSchedulingStrategy(new CounselingSchedulingStrategy());
        else
            appointmentService.setSchedulingStrategy(new ExaminationSchedulingStrategy());
        Collection<Appointment> available = appointmentService.findAvailable(appointmentSearchKit.getTimeSlot());
        return new ResponseEntity<>(available, HttpStatus.OK);
    }
}
