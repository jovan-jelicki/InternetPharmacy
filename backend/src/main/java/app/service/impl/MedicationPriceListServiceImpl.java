package app.service.impl;

import app.model.medication.MedicationPriceList;
import app.repository.MedicationPriceListRepository;
import app.service.MedicationPriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class MedicationPriceListServiceImpl implements MedicationPriceListService {
    private final MedicationPriceListRepository medicationPriceListRepository;

    @Autowired
    public MedicationPriceListServiceImpl(MedicationPriceListRepository medicationPriceListRepository) {
        this.medicationPriceListRepository = medicationPriceListRepository;
    }


    @Override
    public MedicationPriceList save(MedicationPriceList entity) {
        return medicationPriceListRepository.save(entity);
    }

    @Override
    public Collection<MedicationPriceList> read() {
        return medicationPriceListRepository.findAll();
    }

    @Override
    public Optional<MedicationPriceList> read(Long id) {
        return medicationPriceListRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        medicationPriceListRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return medicationPriceListRepository.existsById(id);
    }
}
