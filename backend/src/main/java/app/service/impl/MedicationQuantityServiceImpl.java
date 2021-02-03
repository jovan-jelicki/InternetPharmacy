package app.service.impl;

import app.model.medication.MedicationQuantity;
import app.repository.MedicationQuantityRepository;
import app.service.MedicationQuantityService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class MedicationQuantityServiceImpl implements MedicationQuantityService {
    private final MedicationQuantityRepository medicationQuantityRepository;

    public MedicationQuantityServiceImpl(MedicationQuantityRepository medicationQuantityRepository) {
        this.medicationQuantityRepository = medicationQuantityRepository;
    }

    @Override
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
    public void delete(Long id) {
        medicationQuantityRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return medicationQuantityRepository.existsById(id);
    }
}
