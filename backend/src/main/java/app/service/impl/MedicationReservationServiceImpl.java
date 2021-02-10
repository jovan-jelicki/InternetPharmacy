package app.service.impl;

import app.dto.GetMedicationReservationDTO;
import app.dto.MakeMedicationReservationDTO;
import app.model.medication.MedicationQuantity;
import app.model.medication.MedicationReservation;
import app.model.medication.MedicationReservationStatus;
import app.model.pharmacy.Pharmacy;
import app.model.user.Patient;
import app.model.user.Pharmacist;
import app.repository.MedicationReservationRepository;
import app.repository.PharmacyRepository;
import app.service.EmailService;
import app.service.MedicationReservationService;
import app.service.PatientService;
import app.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedicationReservationServiceImpl implements MedicationReservationService {
    private final MedicationReservationRepository medicationReservationRepository;
    private final PharmacistService pharmacistService;
    private final PatientService patientService;
    private final PharmacyRepository pharmacyRepository;
    private final EmailService emailService;

    @Autowired
    public MedicationReservationServiceImpl(EmailService emailService,PharmacistService pharmacistService,
                                            MedicationReservationRepository medicationReservationRepository,
                                            PharmacyRepository pharmacyRepository, PatientService patientService) {
        this.medicationReservationRepository = medicationReservationRepository;
        this.pharmacistService = pharmacistService;
        this.pharmacyRepository = pharmacyRepository;
        this.emailService = emailService;
        this.patientService = patientService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Scheduled(cron = " 0 0 12 * * ?")
    public void returnMedicationsFromInvalidReservations(){
        Collection<Pharmacy> pharmacies = pharmacyRepository.findAll();
        for(Pharmacy p : pharmacies){
            for(MedicationReservation medicationReservation : p.getMedicationReservation()){
                MedicationReservationStatus medicationReservationStatus = medicationReservation.getStatus();
                if(checkMedicationReservationValid(medicationReservation)){
                    if(medicationReservationStatus == MedicationReservationStatus.requested) {
                        getMedicationQuantityBack(p, medicationReservation.getMedicationQuantity());
                        Patient patient = patientService.read(medicationReservation.getPatient().getId()).get();
                        patient.setPenaltyCount(patient.getPenaltyCount() + 1);
                        patientService.save(patient);
                    }
                }
            }
        }
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
        Pharmacist pharmacist = pharmacistService.read(getMedicationReservationDTO.getPharmacistId()).get();
        List<MedicationReservation> medicationReservationSet = pharmacist.getWorkingHours().getPharmacy().getMedicationReservation();
        try {
            MedicationReservation medicationReservation = medicationReservationSet.stream().filter(m -> m.getId() == getMedicationReservationDTO.getMedicationId()).findFirst().get();
            MedicationReservationStatus medicationReservationStatus = medicationReservation.getStatus();
            if(!checkMedicationReservationValid(medicationReservation)){
                if(medicationReservationStatus == MedicationReservationStatus.requested)
                    getMedicationQuantityBack(pharmacist.getWorkingHours().getPharmacy() , medicationReservation.getMedicationQuantity());
                return null;
            }
            return medicationReservation;
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    @Async
    public void sendEmailToPatient(Patient patient) {
        try {
            emailService.sendMail(patient.getCredentials().getEmail(), "Medication confirmation", "You have successfully pick up a medication!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<MedicationReservation> findAllByPatientId(Long patientId) {
        return medicationReservationRepository.findAllByPatient_Id(patientId);
    }

    private void getMedicationQuantityBack(Pharmacy pharmacy, MedicationQuantity medicationQuantity) {
        pharmacy.getMedicationQuantity().forEach(quantity -> {
            if(medicationQuantity.getMedication().getId() == quantity.getMedication().getId())
                quantity.addQuantity(medicationQuantity.getQuantity());
        });
        pharmacyRepository.save(pharmacy);
    }

    private boolean checkMedicationReservationValid(MedicationReservation medicationReservation){
        if(medicationReservation.getStatus() != MedicationReservationStatus.requested || medicationReservation.getPickUpDate().isBefore(LocalDateTime.now().plusHours(24)))
        {
            if (medicationReservation.getStatus() != MedicationReservationStatus.successful & medicationReservation.getStatus() != MedicationReservationStatus.canceled & medicationReservation.getStatus() != MedicationReservationStatus.denied){
                medicationReservation.setStatus(MedicationReservationStatus.denied);
                medicationReservationRepository.save(medicationReservation);
            }
            return false;
        }
        return true;
    }
    @Override
    public boolean existsById(Long id) {
        return medicationReservationRepository.existsById(id);
    }

    @Override
    public MedicationReservation reserve(MakeMedicationReservationDTO entity) {
        Optional<Pharmacy> pharmacy = pharmacyRepository.findById(entity.getPharmacyId());
//        if(pharmacy.isEmpty())
//            throw new IllegalArgumentException("Pharmacy Id does not exist");
        //proveri stanje u apoteci pre rezervisanja leka
        MedicationReservation medicationReservation = entity.getMedicationReservation();
        medicationReservation.setPatient(patientService.read(medicationReservation.getPatient().getId()).get());
        updateMedicationQuantity(pharmacy.get().getMedicationQuantity(),
                medicationReservation.getMedicationQuantity());
        medicationReservation = this.save(medicationReservation);
        pharmacy.get().getMedicationReservation().add(medicationReservation);
        pharmacyRepository.save(pharmacy.get());
        return medicationReservation;
    }

    @Override
    public boolean cancel(Long reservationId) {
        MedicationReservation medicationReservation = medicationReservationRepository.findById(reservationId).get();
        if(medicationReservation.getPickUpDate().minusHours(24).isBefore(LocalDateTime.now()))
            return false;
        medicationReservation.setStatus(MedicationReservationStatus.canceled);
        save(medicationReservation);
        Pharmacy pharmacy = pharmacyRepository.findAll()
                .stream()
                .filter(p -> p.getMedicationReservation().stream().anyMatch(r -> r.getId() == reservationId))
                .findFirst().orElse(null);
        if(pharmacy == null)
            return false;
        getMedicationQuantityBack(pharmacy, medicationReservation.getMedicationQuantity());
        return true;
    }

    private void updateMedicationQuantity(List<MedicationQuantity> quantities, MedicationQuantity medicationQuantity) {
        quantities.forEach(quantity -> {
            if(medicationQuantity.getMedication().getId() == quantity.getMedication().getId())
                quantity.subtractQuantity(medicationQuantity.getQuantity());
        });
    }
}
