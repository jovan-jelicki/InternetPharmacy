package app.controller.impl;

import app.dto.AppointmentListingDTO;
import app.dto.AppointmentSearchDTO;
import app.dto.CounselingSearchDTO;
import app.model.appointment.Appointment;
import app.model.user.EmployeeType;
import app.model.user.Patient;
import app.service.CounselingService;
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
    private final EmailService emailService;

    @Autowired
    public SchedulingControllerImpl(EmailService emailService, CounselingService counselingService, PharmacistService pharmacistService, ExaminationService examinationService) {
        this.counselingService = counselingService;
        this.pharmacistService = pharmacistService;
        this.examinationService = examinationService;
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
        }
        else
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
        return getCounselingEntity(counselingService.findUpcomingByPatientId(id), id);
    }

    @GetMapping(value = "/examination-upcoming/{id}")
    public ResponseEntity<Collection<AppointmentListingDTO>> findUpcomingExaminationsByPatientId(@PathVariable Long id) {
        return getCounselingEntity(examinationService.findUpcomingByPatientId(id), id);
    }

    @GetMapping(value = "/counseling-previous/{id}")
    public ResponseEntity<Collection<AppointmentListingDTO>> findPreviousCounselingsByPatientId(@PathVariable Long id) {
        return getExaminationResponseEntity(counselingService.findPreviousByPatientId(id), id);
    }

    @GetMapping(value = "/examination-previous/{id}")
    public ResponseEntity<Collection<AppointmentListingDTO>> findPreviousExaminationsByPatientId(@PathVariable Long id) {
        return getExaminationResponseEntity(examinationService.findPreviousByPatientId(id), id);
    }

    private ResponseEntity<Collection<AppointmentListingDTO>> getCounselingEntity(Collection<Appointment> upcomingByPatientId, @PathVariable Long id) {
        return getCollectionResponseEntity(upcomingByPatientId);
    }

    private ResponseEntity<Collection<AppointmentListingDTO>> getExaminationResponseEntity(Collection<Appointment> previousByPatientId, @PathVariable Long id) {
        return getCollectionResponseEntity(previousByPatientId);
    }

    private ResponseEntity<Collection<AppointmentListingDTO>> getCollectionResponseEntity(Collection<Appointment> service) {
        Collection<AppointmentListingDTO> appointments = service
                .stream()
                .map(a -> {
                    AppointmentListingDTO listing = new AppointmentListingDTO(a);
                    listing.setDermatologistFirstName(pharmacistService.read(a.getExaminerId()).get().getFirstName());
                    listing.setDermatologistLastName(pharmacistService.read(a.getExaminerId()).get().getLastName());
                    return listing;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}
