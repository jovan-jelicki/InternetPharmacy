package app.service.impl;

import app.dto.GetMedicationReservationDTO;
import app.model.medication.MedicationReservation;
import app.model.medication.MedicationReservationStatus;
import app.model.user.Pharmacist;
import app.repository.MedicationReservationRepository;
import app.repository.PharmacistRepository;
import app.service.MedicationReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MedicationReservationServiceImpl implements MedicationReservationService {
    private final MedicationReservationRepository medicationReservationRepository;
    private final PharmacistRepository pharmacistRepository;

    @Autowired
    public MedicationReservationServiceImpl(PharmacistRepository pharmacistRepository,MedicationReservationRepository medicationReservationRepository) {
        this.medicationReservationRepository = medicationReservationRepository;
        this.pharmacistRepository = pharmacistRepository;
    }

    @Override
    public MedicationReservation save(MedicationReservation entity) {
        return medicationReservationRepository.save(entity);
    }

    @Override
    public Collection<MedicationReservation> read() {
        return medicationReservationRepository.findAll();
    }

    @Override
    public Optional<MedicationReservation> read(Long id) {
        return medicationReservationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        medicationReservationRepository.deleteById(id);
    }

    @Override
    public MedicationReservation getMedicationReservationFromPharmacy(GetMedicationReservationDTO getMedicationReservationDTO) {
        Pharmacist pharmacist = pharmacistRepository.findById(getMedicationReservationDTO.getPharmacistId()).get();
        List<MedicationReservation> medicationReservationSet = pharmacist.getWorkingHours().getPharmacy().getMedicationReservation();
        try {
            MedicationReservation medicationReservation = medicationReservationSet.stream().filter(m -> m.getId() == getMedicationReservationDTO.getMedicationId()).findFirst().get();
            if(!checkMedicationReservationValid(medicationReservation))
                return null;
            return medicationReservation;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean checkMedicationReservationValid(MedicationReservation medicationReservation){
        if(medicationReservation.getStatus() != MedicationReservationStatus.requested || medicationReservation.getPickUpDate().isBefore(LocalDateTime.now().plusHours(24))) {
            if (medicationReservation.getStatus() != MedicationReservationStatus.successful & medicationReservation.getStatus() != MedicationReservationStatus.canceled)
                medicationReservation.setStatus(MedicationReservationStatus.denied);
                medicationReservationRepository.save(medicationReservation);
            return false;
        }
        return true;
    }
    @Override
    public boolean existsById(Long id) {
        return medicationReservationRepository.existsById(id);
    }
}
