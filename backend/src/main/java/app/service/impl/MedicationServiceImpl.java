package app.service.impl;

import app.dto.MedicationGradeDTO;
import app.dto.MedicationSearchDTO;
import app.model.grade.GradeType;
import app.model.medication.Ingredient;
import app.model.medication.Medication;
import app.model.medication.MedicationQuantity;
import app.model.pharmacy.Pharmacy;
import app.repository.MedicationRepository;
import app.service.GradeService;
import app.service.MedicationService;
import app.service.PatientService;
import app.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;
    private final PatientService patientService;
    private PharmacyService pharmacyService;
    private final GradeService gradeService;

    @Autowired
    public MedicationServiceImpl(MedicationRepository medicationRepository, PatientService patientService, PharmacyService pharmacyService, GradeService gradeService) {
        this.medicationRepository = medicationRepository;
        this.patientService = patientService;
        this.pharmacyService = pharmacyService;
        this.gradeService = gradeService;
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

    @PostConstruct
    public void init() {
        pharmacyService.setMedicationService(this);
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

    @Override
    public Collection<Medication> getMedicationsNotContainedInPharmacy(Long pharmacyId) {
        Pharmacy pharmacy = pharmacyService.read(pharmacyId).get();
        Set<Medication> pharmacyMedications = new HashSet<>();
        Set<Medication> allMedications = new HashSet<Medication>(this.read());

        for (MedicationQuantity medicationQuantity : pharmacy.getMedicationQuantity()) {
            pharmacyMedications.add(medicationQuantity.getMedication());
        }

        allMedications.removeAll(pharmacyMedications);
        return allMedications;
    }

    @Override
    public Medication getMedicationByName(String name) {
        return medicationRepository.getMedicationByName(name);
    }

    @Override
    public Collection<Medication> getMedicationByNameIsContaining(MedicationSearchDTO name) {
        ArrayList<Medication> medications = new ArrayList<>();
        read().forEach(m -> {
            if(m.isEqual(name))
                medications.add(m);
        });
        return medications;
    }

    @Override
    public Collection<MedicationGradeDTO> readMediactionAndGrades() {
        ArrayList<MedicationGradeDTO> medicationGradeDTOS = new ArrayList<>();
        for(Medication medication: this.read()){
            MedicationGradeDTO medicationGradeDTO=new MedicationGradeDTO();
            medicationGradeDTO.setMedication(medication);
            medicationGradeDTO.setGrade(gradeService.findAverageGradeForEntity(medication.getId(), GradeType.medication));

            medicationGradeDTOS.add(medicationGradeDTO);
        }
        return medicationGradeDTOS;
    }
}
