package app.service.impl;

import app.dto.EPrescriptionSimpleInfoDTO;
import app.dto.MakeEPrescriptionDTO;
import app.dto.PharmacyQRDTO;
import app.model.grade.GradeType;
import app.model.medication.*;
import app.model.pharmacy.Pharmacy;
import app.model.user.Patient;
import app.model.user.PharmacyAdmin;
import app.repository.EPrescriptionRepository;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
@Service
public class EPrescriptionServiceImpl implements EPrescriptionService {
    private final EPrescriptionRepository ePrescriptionRepository;
    private final PharmacyService pharmacyService;
    private final PatientService patientService;
    private final PharmacyAdminService pharmacyAdminService;
    private final EmailService emailService;
    private final MedicationLackingEventService medicationLackingEventService;
    private final  MedicationPriceListService medicationPriceListService;
    private final GradeService gradeService;

    @Autowired
    public EPrescriptionServiceImpl(EmailService emailService, MedicationLackingEventService medicationLackingEventService, PharmacyAdminService pharmacyAdminService, PatientService patientService, EPrescriptionRepository ePrescriptionRepository, PharmacyService pharmacyService, MedicationPriceListService medicationPriceListService, GradeService gradeService) {
        this.ePrescriptionRepository = ePrescriptionRepository;
        this.pharmacyService = pharmacyService;
        this.patientService = patientService;
        this.pharmacyAdminService = pharmacyAdminService;
        this.emailService = emailService;
        this.medicationLackingEventService = medicationLackingEventService;
        this.medicationPriceListService = medicationPriceListService;
        this.gradeService = gradeService;
    }

    @Transactional(readOnly = false)
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
        Patient patient = patientService.read(makeEPrescriptionDTO.getPrescription().getPatient().getId()).get();

        if(patient.getPenaltyCount() >= 3)
            return null;

        makeEPrescriptionDTO.getPrescription().setPatient(patientService.read(makeEPrescriptionDTO.getPrescription().getPatient().getId()).get());
        makeEPrescriptionDTO.getPrescription().setDateIssued(LocalDateTime.now());
        makeEPrescriptionDTO.getPrescription().setStatus(EPrescriptionStatus.pending);
        EPrescription ePrescription =  this.save(makeEPrescriptionDTO.getPrescription());
        return new EPrescriptionSimpleInfoDTO(makeEPrescriptionDTO.getPrescription());
    }

    @Override
    public Collection<EPrescription> findAllByPatientId(Long patientId) {
        return ePrescriptionRepository.findAllByPatient_Id(patientId);
    }

    @Async
    public void notifyPharmacyAdmin(Collection<String> medications, PharmacyAdmin pharmacyAdmin){
        try {
            emailService.sendMail(pharmacyAdmin.getCredentials().getEmail(), "No enough medications", "There are not enough drugs in stock : " + medications);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = false)
    public void updateMedicationQuantity(Collection<MedicationQuantity> medicationQuantities, Collection<MedicationQuantity> medicationQuantitiesOfPharmacy){
        for(MedicationQuantity m : medicationQuantities){
            medicationQuantitiesOfPharmacy.forEach(quantity -> {
                if(m.getMedication().getId() == quantity.getMedication().getId())
                    quantity.subtractQuantity(m.getQuantity());
            });
        }
    }

    @Transactional(readOnly = false)
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

    @Transactional(readOnly = false)
    @Override
    public void delete(Long id) {
        ePrescriptionRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return ePrescriptionRepository.existsById(id);
    }

    @Override
    public Collection<PharmacyQRDTO> getPharmacyForQR(Long ePrescriptionId) {
        Collection<PharmacyQRDTO> pharmacyQRDTOS= new ArrayList<>();
        Collection<Pharmacy> pharmacies= pharmacyService.read();

        EPrescription prescription= this.read(ePrescriptionId).get();

        for (Pharmacy pharmacy : pharmacies){
            for(MedicationQuantity pharmacyQuantity : pharmacy.getMedicationQuantity()) {
                for (MedicationQuantity medicationQuantity : prescription.getMedicationQuantity()) {
                    PharmacyQRDTO pharmacyQRDTO=new PharmacyQRDTO();
                    if (pharmacyQuantity.getMedication().getId()==medicationQuantity.getMedication().getId() && pharmacyQuantity.getQuantity()>medicationQuantity.getQuantity()){
                            pharmacyQRDTO.setName(pharmacy.getName());
                            pharmacyQRDTO.setMedicationName(medicationQuantity.getMedication().getName());
                            pharmacyQRDTO.setAddress(pharmacy.getAddress());
                            pharmacyQRDTO.setPharmacyId(pharmacy.getId());
                            pharmacyQRDTO.setMedicationQuantity(medicationQuantity);
                            pharmacyQRDTO.setMedicationPrice((medicationPriceListService.getMedicationPrice(pharmacy.getId(), pharmacyQuantity.getMedication().getId()))*medicationQuantity.getQuantity());
                            pharmacyQRDTO.setPharmacyGrade(gradeService.findAverageGradeForEntity(pharmacyQuantity.getMedication().getId(), GradeType.medication));
                            pharmacyQRDTO.setePrescriptionId(ePrescriptionId);
                            pharmacyQRDTOS.add(pharmacyQRDTO);
                    }
                }
            }
        }
        return pharmacyQRDTOS;
    }

    @Transactional(readOnly = false)
    @Override
    public Boolean buyMedication(Long pharmacyId, Long prescriptionId) {
        Pharmacy pharmacy= pharmacyService.read(pharmacyId).get();
        EPrescription prescription= this.read(prescriptionId).get();
        Patient patient=patientService.read(prescription.getPatient().getId()).get();

            for(MedicationQuantity pharmacyQuantity : pharmacy.getMedicationQuantity()) {
                for (MedicationQuantity medicationQuantity : prescription.getMedicationQuantity()) {
                    if (pharmacyQuantity.getMedication().getId()==medicationQuantity.getMedication().getId() && pharmacyQuantity.getQuantity()>medicationQuantity.getQuantity()){
                        pharmacyQuantity.setQuantity(pharmacyQuantity.getQuantity()-medicationQuantity.getQuantity());
                        patient.setLoyaltyCount(patient.getLoyaltyCount()+medicationQuantity.getMedication().getLoyaltyPoints()*medicationQuantity.getQuantity());
                    }
                }
            }
        patientService.save(patient);
        patientService.setPatientCategory(patient.getId());
        return pharmacyService.save(pharmacy)!=null;
    }
}
