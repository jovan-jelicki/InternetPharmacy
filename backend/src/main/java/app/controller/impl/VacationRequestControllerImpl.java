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

    @GetMapping (value = "/findByPharmacyAndEmployeeType/{pharmacyId}/{employeeType}")
    public ResponseEntity<Collection<VacationRequestDTO>> findByPharmacyAndEmployeeType(@PathVariable Long pharmacyId, @PathVariable EmployeeType employeeType) {
        return new ResponseEntity<>(vacationRequestService.findByPharmacyIdAndEmployeeType(pharmacyId, employeeType), HttpStatus.OK);
    }

    @GetMapping (value = "/findByEmployeeType/{employeeType}")
    public ResponseEntity<Collection<VacationRequestDTO>> findByEmployeeType(@PathVariable EmployeeType employeeType) {
        return new ResponseEntity<>(vacationRequestService.findByEmployeeType(employeeType), HttpStatus.OK);
    }

    @PutMapping(value = "/confirmVacationRequest",  consumes = "application/json")
    public ResponseEntity<Object> confirmVacationRequest(@RequestBody VacationRequestDTO vacationRequestDTO) {
        if(!vacationRequestService.existsById(vacationRequestDTO.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        vacationRequestService.confirmVacationRequest(vacationRequestDTO);
        if (vacationRequestService.read(vacationRequestDTO.getId()).get().getVacationRequestStatus() == VacationRequestStatus.requested)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping(value = "/rejectVacationRequest", consumes = "application/json")
    public ResponseEntity<Object> update(@RequestBody VacationRequestDTO vacationRequestDTO) {
        if(!vacationRequestService.existsById(vacationRequestDTO.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        vacationRequestService.declineVacationRequest(vacationRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
