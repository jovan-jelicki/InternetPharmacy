package app.service.impl;

import app.dto.EPrescriptionSimpleInfoDTO;
import app.dto.MakeEPrescriptionDTO;
import app.model.medication.EPrescription;
import app.model.medication.Medication;
import app.model.medication.MedicationQuantity;
import app.model.pharmacy.Pharmacy;
import app.repository.EPrescriptionRepository;
import app.service.EPrescriptionService;
import app.service.PatientService;
import app.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EPrescriptionServiceImpl implements EPrescriptionService {
    private final EPrescriptionRepository ePrescriptionRepository;
    private final PharmacyService pharmacyService;
    private final PatientService patientService;

    @Autowired
    public EPrescriptionServiceImpl(PatientService patientService, EPrescriptionRepository ePrescriptionRepository, PharmacyService pharmacyService) {
        this.ePrescriptionRepository = ePrescriptionRepository;
        this.pharmacyService = pharmacyService;
        this.patientService = patientService;
    }

    @Override
    public EPrescriptionSimpleInfoDTO reserveEPrescription(MakeEPrescriptionDTO makeEPrescriptionDTO){
        Pharmacy pharmacy = pharmacyService.read(makeEPrescriptionDTO.getPharmacyId()).get();
        Collection<Medication> medications = makeEPrescriptionDTO.getPrescription().getMedicationQuantity().stream().map(medicationQuantity -> medicationQuantity.getMedication()).collect(Collectors.toList());
        if(patientService.isPatientAllergic(medications, makeEPrescriptionDTO.getPrescription().getPatient().getId()))
            throw new IllegalArgumentException("Patient is allergic!");
        if(pharmacy == null)
            throw new IllegalArgumentException("Pharmacy does not exists!");
        if(!pharmacyService.checkMedicationQuantity(makeEPrescriptionDTO.getPrescription().getMedicationQuantity(), pharmacy))
            throw new IllegalArgumentException("No enough medication!");
        makeEPrescriptionDTO.getPrescription().setDateIssued(LocalDateTime.now());
        this.save(makeEPrescriptionDTO.getPrescription());
        updateMedicationQuantity(makeEPrescriptionDTO.getPrescription().getMedicationQuantity(), pharmacy.getMedicationQuantity());
        pharmacyService.save(pharmacy);
        return new EPrescriptionSimpleInfoDTO(makeEPrescriptionDTO.getPrescription());
    }

    public void updateMedicationQuantity(Collection<MedicationQuantity> medicationQuantities, Collection<MedicationQuantity> medicationQuantitiesOfPharmacy){
        for(MedicationQuantity m : medicationQuantities){
            medicationQuantitiesOfPharmacy.forEach(quantity -> {
                if(m.getMedication().getId() == quantity.getMedication().getId())
                    quantity.subtractQuantity(m.getQuantity());
            });
        }
    }

    @Override
    public EPrescription save(EPrescription entity) {
        return ePrescriptionRepository.save(entity);
    }

    @Override
    public Collection<EPrescription> read() {
        return ePrescriptionRepository.findAll();
    }

    @Override
    public Optional<EPrescription> read(Long id) {
        return ePrescriptionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        ePrescriptionRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return ePrescriptionRepository.existsById(id);
    }
}
