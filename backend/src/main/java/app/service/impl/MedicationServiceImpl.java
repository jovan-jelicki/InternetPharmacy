package app.service.impl;

import app.model.medication.Medication;
import app.repository.MedicationRepository;
import app.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;

    @Autowired
    public MedicationServiceImpl(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Override
    public Medication save(Medication entity) {
        return medicationRepository.save(entity);
    }

    @Override
    public Collection<Medication> read() {
        return medicationRepository.findAll();
    }

    @Override
    public Optional<Medication> read(Long id) {
        return medicationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        medicationRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return medicationRepository.existsById(id);
    }
}
