package app.service;

import app.model.time.VacationRequest;

import java.util.Collection;

public interface VacationRequestService extends CRUDService<VacationRequest> {
    Collection<VacationRequest> findByPharmacy(Long pharmacyId);
}
