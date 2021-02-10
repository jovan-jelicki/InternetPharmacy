package app.controller.impl;

import app.controller.DermatologistController;
import app.dto.*;
import app.model.appointment.Appointment;
import app.model.grade.GradeType;
import app.model.time.WorkingHours;
import app.model.user.Dermatologist;
import app.model.user.EmployeeType;
import app.service.AppointmentService;
import app.service.DermatologistService;
import app.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping(value = "api/dermatologists")
public class DermatologistControllerImpl implements DermatologistController {
    private final DermatologistService dermatologistService;
    private final AppointmentService appointmentService;
    private final GradeService gradeService;

    @Autowired
    public DermatologistControllerImpl(DermatologistService dermatologistService, AppointmentService appointmentService, GradeService gradeService) {
        this.dermatologistService = dermatologistService;
        this.appointmentService = appointmentService;
        this.gradeService = gradeService;
    }


    @PostMapping(consumes = "application/json", value="/save")
    public ResponseEntity<Dermatologist> save(@RequestBody Dermatologist entity) {
        return new ResponseEntity<>(dermatologistService.save(entity), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('dermatologist')")
    @PostMapping(value = "/getFreeAppointments", consumes =  "application/json" )
    public ResponseEntity<Collection<AppointmentListingDTO>> getAllFreeAppointmentsOfDermatologist(@RequestBody DermatologistSchedulingDTO dermatologistSchedulingDTO){
        Collection<Appointment> appointments = appointmentService.GetAllAvailableAppointmentsByExaminerIdAndPharmacyAfterDate(dermatologistSchedulingDTO.getDermatologistId(), EmployeeType.ROLE_dermatologist, LocalDateTime.now(), dermatologistSchedulingDTO.getPharmacyId());
        Collection<AppointmentListingDTO> appointmentListingDTOS = new ArrayList<>();
        for(Appointment a : appointments)
            appointmentListingDTOS.add(new AppointmentListingDTO(a));
        return new ResponseEntity<>(appointmentListingDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('dermatologist')")
    @PutMapping(consumes = "application/json")
    public ResponseEntity<PharmacistDermatologistProfileDTO> update(@RequestBody PharmacistDermatologistProfileDTO entity) {
        if(!dermatologistService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Dermatologist dermatologist = dermatologistService.read(entity.getId()).get();
        dermatologistService.save(entity.convertDtoToDermatologist(dermatologist));
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('dermatologist')")
    @GetMapping(value = "/isAccountApproved/{id}")
    public ResponseEntity<Boolean> isAccountApproved(@PathVariable Long id){
        return new ResponseEntity<>(dermatologistService.read(id).get().getApprovedAccount(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ArrayList<DermatologistDTO>> read() {
        ArrayList<DermatologistDTO> dermatologistDTOS = new ArrayList<>();
        for (Dermatologist dermatologist : dermatologistService.read())
            dermatologistDTOS.add(new DermatologistDTO(dermatologist));
        return new ResponseEntity<>(dermatologistDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin')")
    @GetMapping(value = "/getOneWithWorkingHours/{id}")
    public ResponseEntity<DermatologistDTO> getOneWithWorkingHours(@PathVariable Long id) {
        if (dermatologistService.existsById(id))
            return new ResponseEntity<>(new DermatologistDTO(dermatologistService.read(id).get()), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('dermatologist')")
    @GetMapping(value = "/getPharmacy/{id}")
    public ResponseEntity<Collection<PharmacyNameIdDTO>> getPharmacyOfPharmacist(@PathVariable Long id){
        return new ResponseEntity<>(dermatologistService.getPharmacyOfPharmacist(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('dermatologist')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PharmacistDermatologistProfileDTO> read(@PathVariable Long id) {
        return new ResponseEntity<>(new PharmacistDermatologistProfileDTO(dermatologistService.read(id).get()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!dermatologistService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        dermatologistService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasRole('dermatologist')")
    @PutMapping(value = "/pass")
    public ResponseEntity<Void> changePassword(@RequestBody UserPasswordDTO passwordKit) {
        try {
            dermatologistService.changePassword(passwordKit);
        }
        catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Collection<WorkingHours>> getDermatologistsWorkingHours(long id) {
        Dermatologist dermatologist = dermatologistService.read(id).get();
        return new ResponseEntity<>(dermatologist.getWorkingHours(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin')")
    @GetMapping(value = "/getAllDermatologistNotWorkingInPharmacy/{id}")
    public ResponseEntity<Collection<DermatologistDTO>> getAllDermatologistNotWorkingInPharmacy(@PathVariable Long id) {
        ArrayList<DermatologistDTO> dermatologistDTOS = new ArrayList<>();
        for (Dermatologist dermatologist : dermatologistService.getAllDermatologistNotWorkingInPharmacy(id))
            dermatologistDTOS.add(new DermatologistDTO(dermatologist));
        return new ResponseEntity<>(dermatologistDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin, patient')")
    @GetMapping(value = "/getAllDermatologistWorkingInPharmacy/{id}")
    public ResponseEntity<Collection<DermatologistDTO>> getAllDermatologistWorkingInPharmacy(@PathVariable Long id) {
        ArrayList<DermatologistDTO> dermatologistDTOS = new ArrayList<>();
        for (Dermatologist dermatologist : dermatologistService.getAllDermatologistWorkingInPharmacy(id))
            dermatologistDTOS.add(new DermatologistDTO(dermatologist, gradeService.findAverageGradeForEntity(dermatologist.getId(), GradeType.dermatologist)));

        return new ResponseEntity<>(dermatologistDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin')")
    @PutMapping(value = "/addDermatologistToPharmacy",consumes = "application/json")
    public ResponseEntity<Boolean> addDermatologistToPharmacy(@RequestBody DermatologistDTO entity) {
        if(!dermatologistService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (dermatologistService.addDermatologistToPharmacy(entity))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin')")
    @PutMapping(value = "/deleteDermatologistFromPharmacy/{pharmacyId}",consumes = "application/json")
    public ResponseEntity<Boolean> deleteDermatologistFromPharmacy(@RequestBody DermatologistDTO entity, @PathVariable Long pharmacyId) {
        if(!dermatologistService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (dermatologistService.deleteDermatologistFromPharmacy(pharmacyId, entity))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private Dermatologist convertDTOtoEntity(DermatologistDTO dermatologistDTO) {
        Dermatologist dermatologist = dermatologistService.read(dermatologistDTO.getId()).get();
        dermatologist.setId(dermatologistDTO.getId());
        dermatologist.setWorkingHours(dermatologistDTO.getWorkingHours());
        dermatologist.setFirstName(dermatologistDTO.getFirstName());
        dermatologist.setLastName(dermatologistDTO.getLastName());
        dermatologist.setContact(dermatologistDTO.getContact());

        return dermatologist;
    }

}
