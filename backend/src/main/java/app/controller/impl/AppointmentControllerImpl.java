package app.controller.impl;

import app.dto.*;
import app.model.appointment.Appointment;
import app.model.grade.GradeType;
import app.model.time.Period;
import app.service.AppointmentService;
import app.service.DermatologistService;
import app.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('pharmacist, dermatologist')")
    @PostMapping(consumes = "application/json", value = "/getFinishedByExaminer")
    public ResponseEntity<Collection<AppointmentFinishedDTO>> getFinishedByExaminer(@RequestBody ExaminerDTO examinerDTO){
        return new ResponseEntity<>(appointmentService.getFinishedByExaminer(examinerDTO.getId(), examinerDTO.getType()), HttpStatus.OK);
    }


    @GetMapping(value = "/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("hiii", HttpStatus.OK);
    }

    @GetMapping(value = "/kavali")
    public ResponseEntity<String> kavali() {
        return new ResponseEntity<>("kavali", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('patient')")
    @PostMapping(consumes = "application/json", value = "/getFinishedForComplaint")
    public ResponseEntity<Collection<AppointmentEmployeeDTO>> getFinishedForComplaint(@RequestBody ExaminerDTO examinerDTO) {
        return new ResponseEntity<>(appointmentService.getFinishedForComplaint(examinerDTO.getId(), examinerDTO.getType()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacist, dermatologist')")
    @PostMapping(consumes = "application/json", value = "/searchFinishedAppointments")
    public ResponseEntity<Collection<AppointmentFinishedDTO>> searchFinishedAppointments(@RequestBody SearchFinishedAppointments searchFinishedAppointments){
        Collection<AppointmentFinishedDTO> appointmentListingDTOS = new ArrayList<>();
        for(AppointmentFinishedDTO appointmentFinishedDTO : appointmentService.getFinishedByExaminer(searchFinishedAppointments.getId(), searchFinishedAppointments.getType())){
            String fullName = appointmentFinishedDTO.getPatientFirstName() + appointmentFinishedDTO.getPatientLastName();
            if(fullName.toLowerCase().contains(searchFinishedAppointments.getQuery().toLowerCase().replaceAll("\\s+", "")))
                appointmentListingDTOS.add(appointmentFinishedDTO);
        }
        return new ResponseEntity<>(appointmentListingDTOS, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Collection<Appointment>> read() {
        return new ResponseEntity<>(appointmentService.read(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Appointment>> read(@PathVariable Long id) {
        return new ResponseEntity<>(appointmentService.read(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Object> save(@RequestBody Appointment entity) {
        Period period = new Period();
        period.setPeriodStart(entity.getPeriod().getPeriodStart());
        period.setPeriodEnd(entity.getPeriod().getPeriodStart().plusHours(1));
        entity.setPeriod(period);
        boolean ret = appointmentService.createAvailableAppointment(entity);
        if (ret)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasAnyRole('pharmacyAdmin, patient')")
    public ResponseEntity<Void> update(@RequestBody AppointmentUpdateDTO appointmentDTO) {
        try {
            appointmentService.update(appointmentDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('pharmacist, dermatologist')")
    @PostMapping(value = "/getAllFinishedByPatientAndExaminer", consumes = "application/json")
    public ResponseEntity<Collection<AppointmentScheduledDTO>> getAllFinishedByPatientAndExaminerType(@RequestBody PatientAppointmentsSearch patientAppointmentsSearch){
        Collection<AppointmentScheduledDTO> appointmentScheduledDTOS = new ArrayList<>();
        for(Appointment a : appointmentService.getAllFinishedByPatientAndExaminerType(patientAppointmentsSearch.getPatientId(), patientAppointmentsSearch.getType()))
            appointmentScheduledDTOS.add(new AppointmentScheduledDTO(a));
        return new ResponseEntity<>(appointmentScheduledDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('dermatologist, pharmacist')")
    @PutMapping(consumes = "application/json", value = "/finishAppointment")
    public ResponseEntity<Boolean> finishAppointment(@RequestBody AppointmentScheduledDTO appointmentScheduledDTO) {
        return new ResponseEntity<>(appointmentService.finishAppointment(appointmentScheduledDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/counseling")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Void> scheduleCounseling(@RequestBody Appointment entity) {
        if(appointmentService.scheduleCounseling(entity) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping(value = "/cancel-counseling/{id}")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Void> cancelCounseling(@PathVariable Long id) {
        if(appointmentService.cancelCounseling(id) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/cancel-examination/{id}")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Void> cancelExamination(@PathVariable Long id) {
        if(appointmentService.cancelExamination(id) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacist, dermatologist')")
    @PostMapping(value = "/getEvents")
    public ResponseEntity<Collection<EventDTO>> getEventsByExaminer(@RequestBody ExaminerDTO examinerDTO){
        return new ResponseEntity<>(appointmentService.getAllEventsOfExaminer(examinerDTO.getId(), examinerDTO.getType()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacist, dermatologist')")
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

    @PreAuthorize("hasAnyRole('patient')")
    @GetMapping(value = "/getAllAvailableUpcomingDermatologistAppointmentsByPharmacy/{phId}/{ptId}")
    public ResponseEntity<Collection<AppointmentListingDTO>> getAllAvailableUpcomingDermatologistAppointmentsByPharmacyAndPatient(@PathVariable("phId") Long phId, @PathVariable("ptId") Long ptId){
        ArrayList<AppointmentListingDTO> appointmentListingDTOS = new ArrayList<>();
        for (Appointment appointment : appointmentService.getAllAvailableUpcomingDermatologistAppointmentsByPharmacyAndPatient(phId, ptId)) {
            AppointmentListingDTO appointmentListingDTO = new AppointmentListingDTO(appointment);
            appointmentListingDTO.setDermatologistFirstName(dermatologistService.read(appointment.getExaminerId()).get().getFirstName());
            appointmentListingDTO.setDermatologistGrade(gradeService.findAverageGradeForEntity(appointment.getExaminerId(), GradeType.dermatologist));
            appointmentListingDTO.setDermatologistLastName(dermatologistService.read(appointment.getExaminerId()).get().getLastName());
            appointmentListingDTOS.add(appointmentListingDTO);
        }
        return new ResponseEntity<>(appointmentListingDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin')")
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

    @GetMapping(value = "/getAppointmentsPharmacyForComplaint/{patientId}")
    public ResponseEntity<Collection<PharmacyNameIdDTO>> getAppointmentsPharmacyForCoomplaint(@PathVariable Long patientId) {
        Collection<PharmacyNameIdDTO> pharmacy=appointmentService.getAppointmentsPharmacyForComplaint(patientId);
        return new ResponseEntity(pharmacy, HttpStatus.OK);
    }

}
