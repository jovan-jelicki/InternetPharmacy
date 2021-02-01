package app.controller.impl;

import app.dto.AppointmentListingDTO;
import app.dto.AppointmentSearchDTO;
import app.dto.CounselingSearchDTO;
import app.model.appointment.Appointment;
import app.model.user.EmployeeType;
import app.service.CounselingService;
import app.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/scheduling")
public class SchedulingControllerImpl {
    private CounselingService counselingService;
    private PharmacistService pharmacistService;

    @Autowired
    public SchedulingControllerImpl(CounselingService counselingService, PharmacistService pharmacistService) {
        this.counselingService = counselingService;
        this.pharmacistService = pharmacistService;
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

    @GetMapping(value = "/counseling-upcoming/{id}")
    public ResponseEntity<Collection<AppointmentListingDTO>> findUpcomingByPatientId(@PathVariable Long id) {
        Collection<AppointmentListingDTO> appointments = counselingService
                .findUpcomingByPatientId(id)
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

    @GetMapping(value = "/counseling-previous/{id}")
    public ResponseEntity<Collection<AppointmentListingDTO>> findPreviousByPatientId(@PathVariable Long id) {
        Collection<AppointmentListingDTO> appointments = counselingService
                .findPreviousByPatientId(id)
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
