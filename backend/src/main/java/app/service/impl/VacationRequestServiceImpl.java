package app.service.impl;

import app.model.time.VacationRequest;
import app.repository.VacationRequestRepository;
import app.service.VacationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class VacationRequestServiceImpl implements VacationRequestService {
    private final VacationRequestRepository vacationRequestRepository;

    @Autowired
    public VacationRequestServiceImpl(VacationRequestRepository vacationRequestRepository) {
        this.vacationRequestRepository = vacationRequestRepository;
    }

    @Override
    public VacationRequest save(VacationRequest entity) {
        return vacationRequestRepository.save(entity);
    }

    @Override
    public Collection<VacationRequest> read() {
        return vacationRequestRepository.findAll();
    }

    @Override
    public Optional<VacationRequest> read(Long id) {
        return vacationRequestRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        vacationRequestRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return vacationRequestRepository.existsById(id);
    }

    @Override
    public Collection<VacationRequest> findByPharmacy(Long pharmacyId) {
        return vacationRequestRepository.findByPharmacyId(pharmacyId);
    }
}
