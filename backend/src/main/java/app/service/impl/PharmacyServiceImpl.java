package app.service.impl;

import app.dto.AddMedicationToPharmacyDTO;
import app.dto.PharmacySearchDTO;
import app.model.medication.Medication;
import app.model.medication.MedicationQuantity;
import app.model.pharmacy.Pharmacy;
import app.repository.PharmacyRepository;
import app.service.MedicationService;
import app.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private MedicationService medicationService;

    @Autowired
    public PharmacyServiceImpl(PharmacyRepository pharmacyRepository) {
        this.pharmacyRepository = pharmacyRepository;
    }

    @Override
    public void setMedicationService(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @Override
    public Pharmacy save(Pharmacy entity) {
        return pharmacyRepository.save(entity);
    }

    @Override
    public Collection<Pharmacy> read() {
        return pharmacyRepository.findAll();
    }

    @Override
    public Optional<Pharmacy> read(Long id) {
        return pharmacyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        pharmacyRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return pharmacyRepository.existsById(id);
    }

    @Override
    public Collection<Pharmacy> searchByNameAndAddress(PharmacySearchDTO pharmacySearchDTO) {
        ArrayList<Pharmacy> pharmacies = new ArrayList<>();
        read().forEach(p -> {
            if(p.isEqual(pharmacySearchDTO))
                pharmacies.add(p);
        });
        return pharmacies;
    }

    @Override
    public Boolean checkMedicationQuantity(Collection<MedicationQuantity> medicationQuantities, Pharmacy pharmacy) {
        MedicationQuantity medicationQuantity = new MedicationQuantity();
        for(MedicationQuantity m : pharmacy.getMedicationQuantity()) {
            medicationQuantity =  medicationQuantities.stream().filter(med -> med.getMedication().getId() == m.getMedication().getId()).findFirst().orElse(null);
            if(medicationQuantity != null)
                if (m.getQuantity() - medicationQuantity.getQuantity() < 0)
                     return false;
        }
        return true;
    }

    @Override
    public Boolean addNewMedication(AddMedicationToPharmacyDTO addMedicationToPharmacyDTO) {
        Pharmacy pharmacy = this.read(addMedicationToPharmacyDTO.getPharmacyId()).get();
        //circular dependency
        Medication medication = medicationService.read(addMedicationToPharmacyDTO.getMedicationId()).get();
        pharmacy.getMedicationQuantity().add(new MedicationQuantity(medication, addMedicationToPharmacyDTO.getQuantity()));

        this.save(pharmacy);

        return null;
    }




}
