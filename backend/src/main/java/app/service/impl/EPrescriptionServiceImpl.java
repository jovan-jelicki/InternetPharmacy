package app.service.impl;

import app.dto.MakeEPrescriptionDTO;
import app.model.medication.EPrescription;
import app.model.medication.MedicationQuantity;
import app.model.pharmacy.Pharmacy;
import app.repository.EPrescriptionRepository;
import app.service.EPrescriptionService;
import app.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class EPrescriptionServiceImpl implements EPrescriptionService {
    private final EPrescriptionRepository ePrescriptionRepository;
    private final PharmacyService pharmacyService;

    @Autowired
    public EPrescriptionServiceImpl(EPrescriptionRepository ePrescriptionRepository, PharmacyService pharmacyService) {
        this.ePrescriptionRepository = ePrescriptionRepository;
        this.pharmacyService = pharmacyService;
    }

    @Override
    public EPrescription reserveEPrescription(MakeEPrescriptionDTO makeEPrescriptionDTO){
        Pharmacy pharmacy = pharmacyService.read(makeEPrescriptionDTO.getPharmacyId()).get();
        if(pharmacy == null)
            throw new IllegalArgumentException("Pharmacy does not exists!");
        if(pharmacyService.checkMedicationQuantity(makeEPrescriptionDTO.getPrescription().getMedicationQuantity(), pharmacy))
            throw new IllegalArgumentException("No enough medication!");
        this.save(makeEPrescriptionDTO.getPrescription());
        updateMedicationQuantity(makeEPrescriptionDTO.getPrescription().getMedicationQuantity(), pharmacy.getMedicationQuantity());
        pharmacyService.save(pharmacy);
        return makeEPrescriptionDTO.getPrescription();
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
