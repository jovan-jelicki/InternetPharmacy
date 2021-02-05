package app.service.impl;

import app.dto.EPrescriptionSimpleInfoDTO;
import app.dto.MakeEPrescriptionDTO;
import app.dto.MedicationPlainDTO;
import app.model.medication.*;
import app.model.pharmacy.Pharmacy;
import app.model.user.PharmacyAdmin;
import app.repository.EPrescriptionRepository;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.search.SearchTerm;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EPrescriptionServiceImpl implements EPrescriptionService {
    private final EPrescriptionRepository ePrescriptionRepository;
    private final PharmacyService pharmacyService;
    private final PatientService patientService;
    private final PharmacyAdminService pharmacyAdminService;
    private final EmailService emailService;
    private final MedicationLackingEventService medicationLackingEventService;

    @Autowired
    public EPrescriptionServiceImpl(EmailService emailService,MedicationLackingEventService medicationLackingEventService ,PharmacyAdminService pharmacyAdminService, PatientService patientService, EPrescriptionRepository ePrescriptionRepository, PharmacyService pharmacyService) {
        this.ePrescriptionRepository = ePrescriptionRepository;
        this.pharmacyService = pharmacyService;
        this.patientService = patientService;
        this.pharmacyAdminService = pharmacyAdminService;
        this.emailService = emailService;
        this.medicationLackingEventService = medicationLackingEventService;
    }

    @Override
    public EPrescriptionSimpleInfoDTO reserveEPrescription(MakeEPrescriptionDTO makeEPrescriptionDTO){
        Pharmacy pharmacy = pharmacyService.read(makeEPrescriptionDTO.getPharmacyId()).get();
        Collection<Medication> medications = makeEPrescriptionDTO.getPrescription().getMedicationQuantity().stream().map(medicationQuantity -> medicationQuantity.getMedication()).collect(Collectors.toList());
        if(patientService.isPatientAllergic(medications, makeEPrescriptionDTO.getPrescription().getPatient().getId()))
            throw new IllegalArgumentException("Patient is allergic!");

        if(pharmacy == null)
            throw new IllegalArgumentException("Pharmacy does not exists!");

        if(!pharmacyService.checkMedicationQuantity(makeEPrescriptionDTO.getPrescription().getMedicationQuantity(), pharmacy)) {
            for (MedicationQuantity m : makeEPrescriptionDTO.getPrescription().getMedicationQuantity())
                medicationLackingEventService.save(new MedicationLackingEvent(makeEPrescriptionDTO.getExaminerId(), makeEPrescriptionDTO.getEmployeeType(), m.getMedication() , LocalDateTime.now() , makeEPrescriptionDTO.getPharmacyId()));
            PharmacyAdmin pharmacyAdmin = pharmacyAdminService.getPharmacyAdminByPharmacy(pharmacy.getId());
            List<String> namesList = medications.stream().map(Medication::getName).collect(Collectors.toList());
            new Thread(new Runnable() {
                public void run(){
                    notifyPharmacyAdmin(namesList, pharmacyAdmin);
                }
            }).start();
            return null;
        }
        makeEPrescriptionDTO.getPrescription().setDateIssued(LocalDateTime.now());
        makeEPrescriptionDTO.getPrescription().setStatus(EPrescriptionStatus.pending);
        EPrescription ePrescription =  this.save(makeEPrescriptionDTO.getPrescription());
        updateMedicationQuantity(makeEPrescriptionDTO.getPrescription().getMedicationQuantity(), pharmacy.getMedicationQuantity());
        pharmacy.getPrescriptions().add(ePrescription);
        pharmacyService.save(pharmacy);
        return new EPrescriptionSimpleInfoDTO(makeEPrescriptionDTO.getPrescription());
    }

    @Override
    public Collection<EPrescription> findAllByPatientId(Long patientId) {
        return ePrescriptionRepository.findAllByPatient_Id(patientId);
    }

    @Override
    public Collection<MedicationPlainDTO> findAllMedicationsInEPrescriptionByPatientId(Long patientId) {
        Set<MedicationPlainDTO> medicationSet = new HashSet<>();
        ePrescriptionRepository
                .findAllByPatient_Id(patientId)
                .forEach(p -> {
                    p.getMedicationQuantity().forEach(m -> {
                        medicationSet.add(new MedicationPlainDTO(m.getMedication()));
                    });
                });
        return medicationSet;
    }

    @Async
    public void notifyPharmacyAdmin(Collection<String> medications, PharmacyAdmin pharmacyAdmin){
        try {
            emailService.sendMail(pharmacyAdmin.getCredentials().getEmail(), "No enough medications", "There are not enough drugs in stock : " + medications);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
