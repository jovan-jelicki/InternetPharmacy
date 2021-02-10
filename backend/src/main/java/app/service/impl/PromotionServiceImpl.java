package app.service.impl;

import app.model.medication.Medication;
import app.model.pharmacy.Pharmacy;
import app.model.pharmacy.Promotion;
import app.model.time.Period;
import app.model.user.Patient;
import app.repository.PromotionRepository;
import app.service.EmailService;
import app.service.PatientService;
import app.service.PharmacyService;
import app.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final PharmacyService pharmacyService;
    private final PatientService patientService;
    private final EmailService emailService;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository, PharmacyService pharmacyService, PatientService patientService, EmailService emailService) {
        this.promotionRepository = promotionRepository;
        this.pharmacyService = pharmacyService;
        this.patientService = patientService;
        this.emailService = emailService;
    }


    @PostConstruct
    public void init() {
        pharmacyService.setPromotionService(this);
    }


    @Override
    @Transactional(readOnly = false)
    public Promotion save(Promotion entity) {
        return promotionRepository.save(entity);
    }

    @Override
    public Collection<Promotion> read() {
        return promotionRepository.findAll();
    }

    @Override
    public Optional<Promotion> read(Long id) {
        return promotionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        promotionRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return promotionRepository.existsById(id);
    }

    private List<Medication> getRandomElement(List<Medication> list, int totalItems)
    {
        Random rand = new Random();
        List<Medication> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {
            int randomIndex = rand.nextInt(list.size());
            newList.add(list.get(randomIndex));
            list.remove(randomIndex);
        }
        return newList;
    }

    private String generatePromotionContent(List<Medication> medications) {
        String promotionContent = "These 3 medications are 50% of : ";
        for (Medication medication : medications)
            promotionContent += medication.getName() + ",";
        promotionContent = promotionContent.substring(0, promotionContent.length() - 1);
        promotionContent += ".";

        return promotionContent;
    }

    private void sendEmailToUser(Patient patient, Promotion promotion) {
        String emailBody = "";
        String subject = "New promotion in pharmacy " + promotion.getPharmacy().getName();

        emailBody = "Dear " + patient.getFirstName() + " " + patient.getLastName() + ",\nwe are pleased to inform you" +
                " about our new ongoing promotion in pharmacy " + promotion.getPharmacy().getName() + ".\nOn promotion the following medications are 50% off :\n";

        for (Medication medication : promotion.getMedicationsOnPromotion())
            emailBody += medication.getName() + "\n";

        emailBody += "The promotion is valid from " + promotion.getPeriod().getPeriodStart().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + " till " + promotion.getPeriod().getPeriodEnd().format(DateTimeFormatter.ISO_LOCAL_DATE) + ".\n\n" +
                "Sincerely, WebPharm.";

        String email = "david.drvar.bogdanovic@gmail.com";
        emailService.sendMail(email, subject, emailBody);
    }



    private void notifyUsers(Promotion promotion) {
        for (Patient patient : this.getAllPatientsSubscribedToPharmacyPromotions(promotion.getPharmacy()))
            sendEmailToUser(patient, promotion);
    }

    //jednom mesecno se runnuje - svake srede 50% popusta na ova 3 leka
    //TODO change this for the final version
    @Scheduled(fixedRate=500000, initialDelay = 50)
    @Transactional(readOnly = false)
    public void createNewPromotionsForAllPharmacies() {
        Period promotionPeriod = new Period(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0), LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).plusDays(7));
        for (Pharmacy pharmacy : pharmacyService.read()) {
            if (pharmacy.getMedicationQuantity().size() < 3)
                continue;
            ArrayList<Medication> pharmacyMedications = new ArrayList<>();
            pharmacy.getMedicationQuantity().forEach(medicationQuantity -> pharmacyMedications.add(medicationQuantity.getMedication()));

            ArrayList<Medication> medicationsOnPromotion = (ArrayList<Medication>) getRandomElement(pharmacyMedications,3);

            Promotion promotion = new Promotion(promotionPeriod, generatePromotionContent(medicationsOnPromotion), medicationsOnPromotion, pharmacy);

            notifyUsers(promotion);

            promotionRepository.save(promotion);
        }
    }

    @Override
    public Collection<Promotion> getCurrentPromotionsByPharmacyAndDate(Long pharmacyId, LocalDateTime date) {
        return promotionRepository.getCurrentPromotionsByPharmacyAndDate(pharmacyId, date);
    }



    @Override
    public Boolean checkPatientSubscribedToPromotion(Long pharmacyId, Long patientId, Long medicationId) {
        ArrayList<Promotion> promotions = (ArrayList<Promotion>) promotionRepository.getCurrentPromotionsByPharmacyAndDate(pharmacyId, LocalDateTime.now());
        Patient patient = patientService.read(patientId).get();
        for (Promotion promotionPatient : patient.getPromotions())
            for (Promotion promotionPharmacy : promotions)
                if (promotionPatient.getId().equals(promotionPharmacy.getId()) && promotionPharmacy.getMedicationsOnPromotion()
                        .stream().filter(medication -> medication.getId().equals(medicationId)).count() != 0)
                    return true;

        return false;
    }

    public Boolean checkIfSubscribe(Long promotionId, Long patientId) {
        Patient patient = patientService.read(patientId).get();
        for (Promotion promotionPatient : patient.getPromotions()){
            if (promotionPatient.getId().equals(promotionId)){
                return true;
            }
        }
        return  false;
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean subscribeToPromotion(Long patientId, Long promotionId) {
        Patient patient = patientService.read(patientId).get();
        Promotion promotion = this.read(promotionId).get();

        if (this.checkIfSubscribe(promotionId,patientId))// && promotion.getPeriod().getPeriodEnd().isBefore(LocalDateTime.now()))
            return false;

        patient.getPromotions().add(promotion);

        return patientService.save(patient) != null;
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean unsubscribe(Long pharmacyId, Long patientId) {
        Patient patient = patientService.read(patientId).get();
        ArrayList<Promotion> promotions = (ArrayList<Promotion>) promotionRepository.getPromotionByPharmacy(pharmacyId);

        for(Promotion promotion : promotions) {
            patient.getPromotions().remove(promotion);
        }
        return patientService.save(patient) != null;
    }

    @Override
    public Collection<Patient> getAllPatientsSubscribedToPharmacyPromotions(Pharmacy pharmacy) {
        return patientService.read().stream()
                .filter(patient -> patient.getPromotions().stream()
                        .filter(promotion -> promotion.getPharmacy().getId().equals(pharmacy.getId())).count()!=0).collect(Collectors.toList());
    }
}
