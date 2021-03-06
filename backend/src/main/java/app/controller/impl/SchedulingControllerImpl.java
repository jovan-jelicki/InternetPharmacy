package app.controller.impl;

import app.dto.*;
import app.model.appointment.Appointment;
import app.model.grade.GradeType;
import app.model.user.EmployeeType;
import app.model.user.Patient;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/scheduling")
public class SchedulingControllerImpl {
    private final CounselingService counselingService;
    private final ExaminationService examinationService;
    private final PharmacistService pharmacistService;
    private final DermatologistService dermatologistService;
    private final EmailService emailService;
    private final GradeService gradeService;
    private final PatientService patientService;

    @Autowired
    public SchedulingControllerImpl(PatientService patientService, CounselingService counselingService, PharmacistService pharmacistService, ExaminationService examinationService, DermatologistService dermatologistService, EmailService emailService, GradeService gradeService) {
        this.counselingService = counselingService;
        this.pharmacistService = pharmacistService;
        this.examinationService = examinationService;
        this.dermatologistService = dermatologistService;
        this.emailService = emailService;
        this.gradeService = gradeService;
        this.patientService = patientService;
    }

    @PostMapping(value = "/search", consumes = "application/json")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Collection<CounselingSearchDTO>> getAvailable(@RequestBody AppointmentSearchDTO appointmentSearchKit) {
        if (appointmentSearchKit.getEmployeeType() == EmployeeType.ROLE_pharmacist) {
            Collection<CounselingSearchDTO> available = new ArrayList<>();
            counselingService.findAvailablePharmacists(appointmentSearchKit).forEach(p -> {
                CounselingSearchDTO counselingSearchDTO = new CounselingSearchDTO(p);
                PharmacyPlainDTO pharmacy = counselingSearchDTO.getPharmacyDTO();
                pharmacy.setGrade(gradeService.findAverageGradeForEntity(pharmacy.getId(), GradeType.pharmacy));
                PharmacistPlainDTO pharmacist = counselingSearchDTO.getPharmacistPlainDTO();
                pharmacist.setGrade(gradeService.findAverageGradeForEntity(pharmacist.getId(), GradeType.pharmacist));
                available.add(new CounselingSearchDTO(p));
            });
            return new ResponseEntity<>(available, HttpStatus.OK);
        } else
            // ZA DERMATOLOGE KASNIJE DODATI
            return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('dermatologist')")
    @PostMapping(value = "/dermatologistSchedulingCreatedAppointment", consumes = "application/json")
    public ResponseEntity<Boolean> dermatologistSchedulingCreatedAppointment(@RequestBody AppointmentUpdateDTO appointmentUpdateDTO){
        if(examinationService.dermatologistSchedulingCreatedAppointment(appointmentUpdateDTO)) {
            sendNotificationToPatient(patientService.read(appointmentUpdateDTO.getPatientId()).get());
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);    }

    @PreAuthorize("hasRole('dermatologist')")
    @PostMapping(value = "/dermatologistScheduling", consumes = "application/json")
    public ResponseEntity<Boolean> dermatologistScheduling(@RequestBody Appointment appointment){
        if(examinationService.dermatologistScheduling(appointment)) {
            sendNotificationToPatient(appointment.getPatient());
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }

    @PreAuthorize("hasRole('pharmacist')")
    @PostMapping(value = "/pharmacistScheduling", consumes = "application/json")
    public ResponseEntity<Boolean> pharmacistScheduling(@RequestBody Appointment appointment){
        if(counselingService.pharmacistScheduling(appointment)) {
            sendNotificationToPatient(appointment.getPatient());
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }

    @Async
    public void sendNotificationToPatient(Patient patient){
        try {
            new Thread(new Runnable() {
                public void run(){
                    emailService.sendMail(patient.getCredentials().getEmail(), "New appointemnt", "You have new appointment scheduled!");
                }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @GetMapping(value = "/counseling-upcoming/{id}")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Collection<AppointmentListingDTO>> findUpcomingCounselingsByPatientId(@PathVariable Long id) {
        Collection<AppointmentListingDTO> appointments = filterCounselings(counselingService.findUpcomingByPatientId(id));
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping(value = "/examination-upcoming/{id}")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Collection<AppointmentListingDTO>> findUpcomingExaminationsByPatientId(@PathVariable Long id) {
        Collection<AppointmentListingDTO> appointments = filterExaminations(examinationService.findUpcomingByPatientId(id));
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping(value = "/counseling-previous/{id}")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Collection<AppointmentListingDTO>> findPreviousCounselingsByPatientId(@PathVariable Long id) {
        Collection<AppointmentListingDTO> appointments = filterCounselings(counselingService.findPreviousByPatientId(id));
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping(value = "/examination-previous/{id}")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Collection<AppointmentListingDTO>> findPreviousExaminationsByPatientId(@PathVariable Long id) {
        Collection<AppointmentListingDTO> appointments = filterExaminations(examinationService.findPreviousByPatientId(id));
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    private Collection<AppointmentListingDTO> filterCounselings(Collection<Appointment> appointments) {
        return appointments
                .stream()
                .map(a -> {
                    AppointmentListingDTO listing = new AppointmentListingDTO(a);
                    listing.setDermatologistFirstName(pharmacistService.read(a.getExaminerId()).get().getFirstName());
                    listing.setDermatologistLastName(pharmacistService.read(a.getExaminerId()).get().getLastName());
                    return listing;
                })
                .collect(Collectors.toList());
    }

    private Collection<AppointmentListingDTO> filterExaminations(Collection<Appointment> appointments) {
        return appointments
                .stream()
                .map(a -> {
                    AppointmentListingDTO listing = new AppointmentListingDTO(a);
                    listing.setDermatologistFirstName(dermatologistService.read(a.getExaminerId()).get().getFirstName());
                    listing.setDermatologistLastName(dermatologistService.read(a.getExaminerId()).get().getLastName());
                    return listing;
                })
                .collect(Collectors.toList());
    }
}
