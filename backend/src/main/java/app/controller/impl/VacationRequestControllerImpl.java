package app.controller.impl;

import app.controller.VacationRequestController;
import app.dto.VacationRequestDTO;
import app.dto.VacationRequestSendDTO;
import app.model.time.VacationRequest;
import app.model.time.VacationRequestStatus;
import app.model.user.EmployeeType;
import app.service.VacationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/vacationRequest")
public class VacationRequestControllerImpl implements VacationRequestController {
    private final VacationRequestService vacationRequestService;

    @Autowired
    public VacationRequestControllerImpl(VacationRequestService vacationRequestService) {
        this.vacationRequestService = vacationRequestService;
    }

    @Override
    @PostMapping(consumes = "application/json")
    public ResponseEntity<VacationRequest> save(@RequestBody VacationRequest entity) {
        return new ResponseEntity<>(vacationRequestService.save(entity), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('pharmacist, dermatologist')")
    @PostMapping(consumes = "application/json", value = "/saveDto")
    public ResponseEntity<VacationRequestSendDTO> saveVacationRequest(@RequestBody VacationRequestSendDTO entity) {
        return new ResponseEntity<>(vacationRequestService.saveVacationRequest(entity), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(consumes = "application/json")
    public ResponseEntity<VacationRequest> update(@RequestBody VacationRequest entity) {
        if(!vacationRequestService.existsById(entity.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(vacationRequestService.save(entity), HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<Collection<VacationRequest>> read() {
        return new ResponseEntity<>(vacationRequestService.read(), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<VacationRequest>> read(@PathVariable Long id) {
        return new ResponseEntity<>(vacationRequestService.read(id), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(!vacationRequestService.existsById(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        vacationRequestService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping (value = "/findByPharmacy/{pharmacyId}")
    public ResponseEntity<Collection<VacationRequest>> findByPharmacy(@PathVariable Long pharmacyId) {
        return new ResponseEntity<>(vacationRequestService.findByPharmacy(pharmacyId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin')")
    @GetMapping (value = "/findByPharmacyAndEmployeeType/{pharmacyId}/{employeeType}")
    public ResponseEntity<Collection<VacationRequestDTO>> findByPharmacyAndEmployeeType(@PathVariable Long pharmacyId, @PathVariable EmployeeType employeeType) {
        return new ResponseEntity<>(vacationRequestService.findByPharmacyIdAndEmployeeType(pharmacyId, employeeType), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('systemAdmin')")
    @GetMapping (value = "/findByEmployeeType/{employeeType}")
    public ResponseEntity<Collection<VacationRequestDTO>> findByEmployeeType(@PathVariable EmployeeType employeeType) {
        return new ResponseEntity<>(vacationRequestService.findByEmployeeType(employeeType), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin, systemAdmin')")
    @PutMapping(value = "/confirmVacationRequest",  consumes = "application/json")
    public ResponseEntity<Object> confirmVacationRequest(@RequestBody VacationRequestDTO vacationRequestDTO) {
        if(!vacationRequestService.existsById(vacationRequestDTO.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            vacationRequestService.confirmVacationRequest(vacationRequestDTO);
        }
        catch (ObjectOptimisticLockingFailureException ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (vacationRequestService.read(vacationRequestDTO.getId()).get().getVacationRequestStatus() == VacationRequestStatus.requested)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin, systemAdmin')")
    @PutMapping(value = "/rejectVacationRequest", consumes = "application/json")
    public ResponseEntity<Object> update(@RequestBody VacationRequestDTO vacationRequestDTO) {
        if(!vacationRequestService.existsById(vacationRequestDTO.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            vacationRequestService.declineVacationRequest(vacationRequestDTO);
        }
        catch (ObjectOptimisticLockingFailureException ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (vacationRequestService.read(vacationRequestDTO.getId()).get().getVacationRequestStatus() == VacationRequestStatus.requested)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
