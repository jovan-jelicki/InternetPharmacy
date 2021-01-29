package app.service.impl;

import app.model.medication.Ingredient;
import app.model.medication.Medication;
import app.repository.MedicationRepository;
import app.service.MedicationService;
import app.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;
    private final PatientService patientService;

    @Autowired
    public MedicationServiceImpl(MedicationRepository medicationRepository, PatientService patientService) {
        this.medicationRepository = medicationRepository;
        this.patientService = patientService;
    }

    @Override
    public Collection<Medication> getAllMedicationsPatientIsNotAllergicTo(Long patientId){
        Collection<Medication> medications = new ArrayList<>();
        Collection<Ingredient> ingredients = patientService.getPatientAllergieIngridients(patientId);
        for(Medication m : read())
            if(!m.getIngredient().stream().anyMatch(ingredients::contains))
                medications.add(m);
        return medications;
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
