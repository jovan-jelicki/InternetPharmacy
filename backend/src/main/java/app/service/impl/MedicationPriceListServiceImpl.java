package app.service.impl;

import app.dto.MedicationPriceListDTO;
import app.model.medication.Medication;
import app.model.medication.MedicationPriceList;
import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
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
    public Double GetMedicationPriceInPharmacyByDate(Long pharmacyId, Long medicationId, LocalDateTime date) {
        ArrayList<MedicationPriceList> medicationPriceLists = (ArrayList<MedicationPriceList>) medicationPriceListRepository.GetMedicationPriceInPharmacyByDate(pharmacyId,medicationId,date);
        long maxId = (long) -1.0;
        for (MedicationPriceList medicationPriceList : medicationPriceLists)
            if (medicationPriceList.getId() > maxId)
                maxId = medicationPriceList.getId();

        long finalMaxId = maxId;
        if (medicationPriceLists.size()>0)
            return medicationPriceLists.stream().filter(medicationPriceList -> medicationPriceList.getId() == finalMaxId).findFirst().get().getCost();

        return 400.00; //TODO default price
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

    private boolean isOverlapping (Period periodA, Period periodB) {
        //A A, B B
        //B B, A A
        if (periodA.getPeriodStart().isBefore(periodB.getPeriodStart()) && periodA.getPeriodStart().isBefore(periodB.getPeriodEnd())
            && periodA.getPeriodEnd().isBefore(periodB.getPeriodStart()) && periodA.getPeriodEnd().isBefore(periodB.getPeriodEnd()))
            return false;
        else if (periodB.getPeriodStart().isBefore(periodA.getPeriodStart()) && periodB.getPeriodStart().isBefore(periodA.getPeriodEnd())
            && periodB.getPeriodEnd().isBefore(periodA.getPeriodStart()) && periodB.getPeriodEnd().isBefore(periodA.getPeriodEnd()))
            return false;
        else
            return true;
    }

    @Override
    public Boolean createNewPriceList(MedicationPriceListDTO medicationPriceListDTO) {
        Pharmacy pharmacy = pharmacyService.read(medicationPriceListDTO.getPharmacyId()).get();
        Medication medication = pharmacy.getMedicationQuantity().stream().filter(medicationQuantity -> medicationQuantity.getMedication().getId().equals(medicationPriceListDTO.getMedicationId())).findFirst().get().getMedication();
        MedicationPriceList medicationPriceList = new MedicationPriceList(medication, medicationPriceListDTO.getCost(), medicationPriceListDTO.getPeriod(), pharmacy);


        if (medicationPriceListDTO.getPeriod().getPeriodStart().isAfter(medicationPriceListDTO.getPeriod().getPeriodEnd()))
            return false;
        //da li se overlappuje
        for (MedicationPriceList medicationPriceListFor : medicationPriceListRepository.getMedicationPriceListHistoryByPharmacy(medicationPriceListDTO.getPharmacyId(), medicationPriceListDTO.getMedicationId()))
            if (isOverlapping(medicationPriceListDTO.getPeriod(), medicationPriceListFor.getPeriod()))
                return false;

        return this.save(medicationPriceList) != null;
    }

    @Override
    public Double getMedicationPrice(Long pharmacyId, Long medicationId) {
        Double temp = medicationPriceListRepository.getMedicationPrice(pharmacyId,medicationId,LocalDateTime.now());
        if (temp != null)
            return temp;
        return 400.00;

    }
}
