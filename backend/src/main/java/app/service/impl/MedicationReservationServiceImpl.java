package app.service.impl;

import app.model.medication.MedicationReservation;
import app.repository.MedicationReservationRepository;
import app.service.MedicationReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class MedicationReservationServiceImpl implements MedicationReservationService {
    private final MedicationReservationRepository medicationReservationRepository;

    @Autowired
    public MedicationReservationServiceImpl(MedicationReservationRepository medicationReservationRepository) {
        this.medicationReservationRepository = medicationReservationRepository;
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
    public boolean existsById(Long id) {
        return medicationReservationRepository.existsById(id);
    }
}
