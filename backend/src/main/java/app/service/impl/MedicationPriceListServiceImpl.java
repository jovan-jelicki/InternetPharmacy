package app.service.impl;

import app.dto.MedicationPriceListDTO;
import app.model.medication.MedicationPriceList;
import app.repository.MedicationPriceListRepository;
import app.service.MedicationPriceListService;
import app.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class MedicationPriceListServiceImpl implements MedicationPriceListService {
    private final MedicationPriceListRepository medicationPriceListRepository;
    private final PharmacyService pharmacyService;

    @Autowired
    public MedicationPriceListServiceImpl(MedicationPriceListRepository medicationPriceListRepository, PharmacyService pharmacyService) {
        this.medicationPriceListRepository = medicationPriceListRepository;
        this.pharmacyService = pharmacyService;
    }

    @PostConstruct
    public void init() {
        pharmacyService.setMedicationPriceListService(this);
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

    @Override
    public MedicationPriceList GetMedicationPriceInPharmacyByDate(Long pharmacyId, Long medicationId, LocalDateTime date) {
        return medicationPriceListRepository.GetMedicationPriceInPharmacyByDate(pharmacyId,medicationId,date);
    }

    @Override
    public Collection<MedicationPriceListDTO> getCurrentPriceListsByPharmacy(Long pharmacyId) {
        ArrayList<MedicationPriceListDTO> medicationPriceListDTOS = new ArrayList<>();
        medicationPriceListRepository.getCurrentPriceListsByPharmacy(pharmacyId, LocalDateTime.now()).forEach(medicationPriceList -> {
            medicationPriceListDTOS.add(new MedicationPriceListDTO(medicationPriceList));
        });
        return medicationPriceListDTOS;
    }

    @Override
    public Collection<MedicationPriceListDTO> getMedicationPriceListHistoryByPharmacy(Long pharmacyId, Long medicationId) {
        ArrayList<MedicationPriceListDTO> medicationPriceListDTOS = new ArrayList<>();
        medicationPriceListRepository.getMedicationPriceListHistoryByPharmacy(pharmacyId, medicationId).forEach(medicationPriceList -> {
            medicationPriceListDTOS.add(new MedicationPriceListDTO(medicationPriceList));
        });
        return medicationPriceListDTOS;
    }
}
