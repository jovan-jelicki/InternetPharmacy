package app.controller;

import app.model.time.VacationRequest;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface VacationRequestController extends CRUDController<VacationRequest> {
    ResponseEntity<Collection<VacationRequest>> findByPharmacy(Long pharmacyId);
}
