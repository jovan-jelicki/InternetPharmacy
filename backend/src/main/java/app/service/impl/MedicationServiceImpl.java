package app.service.impl;

import app.model.medication.Ingredient;
import app.model.medication.Medication;
import app.model.medication.SideEffect;
import app.model.user.Patient;
import app.repository.MedicationRepository;
import app.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    @Override
    public Collection<Medication> fetchMedicationAlternatives(Long id) {
        Optional<Medication> patient = medicationRepository.findById(id);
        if(patient.isPresent())
            return patient.get().getAlternatives();
        return new ArrayList<>();
    }
}
