package app.controller.impl;

import app.dto.*;
import app.model.appointment.Appointment;
import app.model.grade.GradeType;
import app.service.AppointmentService;
import app.service.DermatologistService;
import app.service.GradeService;
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
    private final GradeService gradeService;

    @Autowired
    public AppointmentControllerImpl(AppointmentService appointmentService, DermatologistService dermatologistService, GradeService gradeService) {
        this.appointmentService = appointmentService;
        this.dermatologistService = dermatologistService;
        this.gradeService = gradeService;
    }

    @PostMapping(consumes = "application/json", value = "/getFinishedByExaminer")
    public ResponseEntity<Collection<AppointmentFinishedDTO>> getFinishedByExaminer(@RequestBody ExaminerDTO examinerDTO){
        return new ResponseEntity<>(appointmentService.getFinishedByExaminer(examinerDTO.getId(), examinerDTO.getType()), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", value = "/getFinishedForComplaint")
    public ResponseEntity<Collection<AppointmentEmployeeDTO>> getFinishedForComplaint(@RequestBody ExaminerDTO examinerDTO){
        return new ResponseEntity<>(appointmentService.getFinishedForComplaint(examinerDTO.getId(), examinerDTO.getType()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<Appointment>> read() {
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

    @PutMapping(value = "/update")
    public ResponseEntity<Void> update(@RequestBody AppointmentUpdateDTO appointmentDTO) {
        try {
            appointmentService.update(appointmentDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/getAllFinishedByPatientAndExaminer", consumes = "application/json")
    public ResponseEntity<Collection<AppointmentScheduledDTO>> getAllFinishedByPatientAndExaminerType(@RequestBody PatientAppointmentsSearch patientAppointmentsSearch){
        Collection<AppointmentScheduledDTO> appointmentScheduledDTOS = new ArrayList<>();
        for(Appointment a : appointmentService.getAllFinishedByPatientAndExaminerType(patientAppointmentsSearch.getPatientId(), patientAppointmentsSearch.getType()))
            appointmentScheduledDTOS.add(new AppointmentScheduledDTO(a));
        return new ResponseEntity<>(appointmentScheduledDTOS, HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", value = "/finishAppointment")
    public ResponseEntity<Boolean> finishAppointment(@RequestBody AppointmentScheduledDTO appointmentScheduledDTO) {
        return new ResponseEntity<>(appointmentService.finishAppointment(appointmentScheduledDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/counseling")
    public ResponseEntity<Void> scheduleCounseling(@RequestBody Appointment entity) {
        if(appointmentService.scheduleCounseling(entity) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping(value = "/cancel-counseling/{id}")
    public ResponseEntity<Void> cancelCounseling(@PathVariable Long id) {
        if(appointmentService.cancelCounseling(id) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/cancel-examination/{id}")
    public ResponseEntity<Void> cancelExamination(@PathVariable Long id) {
        if(appointmentService.cancelExamination(id) == null)
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

    @GetMapping(value = "/patientDidNotShowUp/{id}")
    public ResponseEntity patientDidNotShowUp(@PathVariable Long id) {
        return new ResponseEntity(appointmentService.patientDidNotShowUp(id), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllAvailableUpcomingDermatologistAppointmentsByPharmacy/{id}")
    public ResponseEntity<Collection<AppointmentListingDTO>> getAllAvailableUpcomingDermatologistAppointmentsByPharmacy(@PathVariable Long id){
        ArrayList<AppointmentListingDTO> appointmentListingDTOS = new ArrayList<>();
        for (Appointment appointment : appointmentService.getAllAvailableUpcomingDermatologistAppointmentsByPharmacy(id)) {
            AppointmentListingDTO appointmentListingDTO = new AppointmentListingDTO(appointment);
            appointmentListingDTO.setDermatologistFirstName(dermatologistService.read(appointment.getExaminerId()).get().getFirstName());
            appointmentListingDTO.setDermatologistGrade(gradeService.findAverageGradeForEntity(appointment.getExaminerId(), GradeType.dermatologist));
            appointmentListingDTO.setDermatologistLastName(dermatologistService.read(appointment.getExaminerId()).get().getLastName());
            appointmentListingDTOS.add(appointmentListingDTO);
        }
        return new ResponseEntity<>(appointmentListingDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/getAppointmentsMonthlyReport/{pharmacyId}")
    public ResponseEntity<Collection<ReportsDTO>> getAppointmentsMonthlyReport(@PathVariable Long pharmacyId) {
        return new ResponseEntity(appointmentService.getAppointmentsMonthlyReport(pharmacyId), HttpStatus.OK);
    }

    @GetMapping(value = "/getAppointmentsQuarterlyReport/{pharmacyId}")
    public ResponseEntity<Collection<ReportsDTO>> getAppointmentsQuarterlyReport(@PathVariable Long pharmacyId) {
        return new ResponseEntity(appointmentService.getAppointmentsQuarterlyReport(pharmacyId), HttpStatus.OK);
    }

    @GetMapping(value = "/getAppointmentsYearlyReport/{pharmacyId}")
    public ResponseEntity<Collection<ReportsDTO>> getAppointmentsYearlyReport(@PathVariable Long pharmacyId) {
        return new ResponseEntity(appointmentService.getAppointmentsYearlyReport(pharmacyId), HttpStatus.OK);
    }
}
