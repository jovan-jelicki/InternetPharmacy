package app.service.impl;

import app.model.medication.MedicationQuantity;
import app.repository.MedicationQuantityRepository;
import app.service.MedicationQuantityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class MedicationQuantityServiceImpl implements MedicationQuantityService {
    private final MedicationQuantityRepository medicationQuantityRepository;

    public MedicationQuantityServiceImpl(MedicationQuantityRepository medicationQuantityRepository) {
        this.medicationQuantityRepository = medicationQuantityRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public MedicationQuantity save(MedicationQuantity entity) {
        return medicationQuantityRepository.save(entity);
    }

    @Override
    public Collection<MedicationQuantity> read() {
        return medicationQuantityRepository.findAll();
    }

    @Override
    public Optional<MedicationQuantity> read(Long id) {
        return medicationQuantityRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        medicationQuantityRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return medicationQuantityRepository.existsById(id);
    }
}
