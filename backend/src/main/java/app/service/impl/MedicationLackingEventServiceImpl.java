package app.service.impl;

import app.dto.MedicationLackingEventDTO;
import app.model.medication.MedicationLackingEvent;
import app.model.user.EmployeeType;
import app.model.user.User;
import app.repository.MedicationLackingEventRepository;
import app.service.DermatologistService;
import app.service.MedicationLackingEventService;
import app.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class MedicationLackingEventServiceImpl implements MedicationLackingEventService {
    private final MedicationLackingEventRepository medicationLackingEventRepository;
    private final DermatologistService dermatologistService;
    private final PharmacistService pharmacistService;

    @Autowired
    public MedicationLackingEventServiceImpl(MedicationLackingEventRepository medicationLackingEventRepository, DermatologistService dermatologistService, PharmacistService pharmacistService) {
        this.medicationLackingEventRepository = medicationLackingEventRepository;
        this.dermatologistService = dermatologistService;
        this.pharmacistService = pharmacistService;
    }


    @Override
    public MedicationLackingEvent save(MedicationLackingEvent entity) {
        return medicationLackingEventRepository.save(entity);
    }

    @Override
    public Collection<MedicationLackingEvent> read() {
        return medicationLackingEventRepository.findAll();
    }

    @Override
    public Optional<MedicationLackingEvent> read(Long id) {
        return medicationLackingEventRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        medicationLackingEventRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return medicationLackingEventRepository.existsById(id);
    }

    @Override
    public Collection<MedicationLackingEvent> getMedicationLackingEventByPharmacyId(Long pharmacyId) {
        return medicationLackingEventRepository.getMedicationLackingEventByPharmacyId(pharmacyId);
    }

    public Collection<MedicationLackingEventDTO> getMedicationLackingEventListing(Long pharmacyId) {
        ArrayList<MedicationLackingEventDTO> medicationLackingEventDTOS = new ArrayList<>();
        for (MedicationLackingEvent medicationLackingEvent : this.getMedicationLackingEventByPharmacyId(pharmacyId)) {
            User user = medicationLackingEvent.getEmployeeType() == EmployeeType.dermatologist ?
                    dermatologistService.read(medicationLackingEvent.getEmployeeId()).get() : pharmacistService.read(medicationLackingEvent.getEmployeeId()).get();
            MedicationLackingEventDTO medicationLackingEventDTO = new MedicationLackingEventDTO(medicationLackingEvent);
            medicationLackingEventDTO.setEmployeeFirstName(user.getFirstName());
            medicationLackingEventDTO.setEmployeeLastName(user.getLastName());
            medicationLackingEventDTOS.add(medicationLackingEventDTO);
        }

        return medicationLackingEventDTOS;
    }
}
