package app.controller.impl;

import app.dto.AppointmentListingDTO;
import app.dto.AppointmentSearchDTO;
import app.dto.CounselingSearchDTO;
import app.model.appointment.Appointment;
import app.model.user.EmployeeType;
import app.model.user.Patient;
import app.service.CounselingService;
import app.service.DermatologistService;
import app.service.EmailService;
import app.service.ExaminationService;
import app.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    public SchedulingControllerImpl(CounselingService counselingService, PharmacistService pharmacistService, ExaminationService examinationService, DermatologistService dermatologistService, EmailService emailService) {
        this.counselingService = counselingService;
        this.pharmacistService = pharmacistService;
        this.examinationService = examinationService;
        this.dermatologistService = dermatologistService;
        this.emailService = emailService;
    }

    @PostMapping(value = "/search", consumes = "application/json")
    public ResponseEntity<Collection<CounselingSearchDTO>> getAvailable(@RequestBody AppointmentSearchDTO appointmentSearchKit) {
        if (appointmentSearchKit.getEmployeeType() == EmployeeType.pharmacist) {
            Collection<CounselingSearchDTO> available = new ArrayList<>();
            counselingService.findAvailablePharmacists(appointmentSearchKit).forEach(p -> {
                available.add(new CounselingSearchDTO(p));
            });
            return new ResponseEntity<>(available, HttpStatus.OK);
        } else
            // ZA DERMATOLOGE KASNIJE DODATI
            return new ResponseEntity<>(null, HttpStatus.OK);
    }

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
    public ResponseEntity<Collection<AppointmentListingDTO>> findUpcomingCounselingsByPatientId(@PathVariable Long id) {
        Collection<AppointmentListingDTO> appointments = filterCounselings(counselingService.findUpcomingByPatientId(id));
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping(value = "/examination-upcoming/{id}")
    public ResponseEntity<Collection<AppointmentListingDTO>> findUpcomingExaminationsByPatientId(@PathVariable Long id) {
        Collection<AppointmentListingDTO> appointments = filterExaminations(examinationService.findUpcomingByPatientId(id));
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping(value = "/counseling-previous/{id}")
    public ResponseEntity<Collection<AppointmentListingDTO>> findPreviousCounselingsByPatientId(@PathVariable Long id) {
        Collection<AppointmentListingDTO> appointments = filterCounselings(counselingService.findPreviousByPatientId(id));
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping(value = "/examination-previous/{id}")
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
