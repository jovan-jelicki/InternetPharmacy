package app.service.impl;

import app.dto.GetMedicationReservationDTO;
import app.dto.PharmacySearchDTO;
import app.model.medication.MedicationReservation;
import app.model.pharmacy.Pharmacy;
import app.repository.PharmacyRepository;
import app.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    @Autowired
    public PharmacyServiceImpl(PharmacyRepository pharmacyRepository) {
        this.pharmacyRepository = pharmacyRepository;
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




}
