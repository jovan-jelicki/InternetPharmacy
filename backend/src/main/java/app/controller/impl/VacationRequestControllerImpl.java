package app.controller.impl;

import app.controller.VacationRequestController;
import app.model.time.VacationRequest;
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
}
