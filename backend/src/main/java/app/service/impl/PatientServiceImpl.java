package app.service.impl;

import app.dto.PharmacyPlainDTO;
import app.dto.UserPasswordDTO;
import app.model.medication.Ingredient;
import app.model.medication.Medication;
import app.model.pharmacy.LoyaltyCategory;
import app.model.pharmacy.LoyaltyScale;
import app.model.user.Patient;
import app.repository.PatientRepository;
import app.service.LoyaltyScaleService;
import app.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;
    private LoyaltyScaleService loyaltyScaleService;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, LoyaltyScaleService loyaltyScaleService) {
        this.patientRepository = patientRepository;
        this.loyaltyScaleService = loyaltyScaleService;
    }

    @Override
    @Transactional(readOnly = false)
    public void changePassword(UserPasswordDTO passwordKit) {
        Optional<Patient> _user = patientRepository.findById(passwordKit.getUserId());
//        if(_user.isEmpty())
//            throw new NullPointerException("User not found");
        Patient user = _user.get();
        validatePassword(passwordKit, user);
        user.getCredentials().setPassword(passwordKit.getNewPassword());
        save(user);
    }

    private void validatePassword(UserPasswordDTO passwordKit, Patient user) {
        String password = user.getCredentials().getPassword();
        if(!password.equals(passwordKit.getOldPassword()))
            throw new IllegalArgumentException("Wrong password");
        else if(!passwordKit.getNewPassword().equals(passwordKit.getRepeatedPassword()))
            throw new IllegalArgumentException("Entered passwords doesn't match");
    }

    @Override
    public Collection<Ingredient> getPatientAllergieIngridients(Long id){
        return read(id).get().getAllergies();
    }

    @Override
    public Boolean isPatientAllergic(Collection<Medication> medications, Long id){
        Collection<Ingredient> ingredients = getPatientAllergieIngridients(id);
        for(Medication medication : medications)
            if(medication.getIngredient().stream().anyMatch(ingredients::contains))
                return true;
        return false;
    }

    @Override
    @Transactional(readOnly = false)
    public Patient save(Patient entity) {
        return patientRepository.save(entity);
    }

    @Override
    public Collection<Patient> read() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> read(Long id) {return patientRepository.findById(id); }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {patientRepository.deleteById(id);}

    @Override
    public boolean existsById(Long id) {
        return patientRepository.existsById(id);
    }

    public Patient findByEmailAndPassword(String email, String password) { return patientRepository.findByEmailAndPassword(email, password);}

    @Override
    public Patient findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    @Override
    public Collection<PharmacyPlainDTO> getPromotionPharmacies(Long patientId) {
        Set<PharmacyPlainDTO> pharmacies = new HashSet<>();
        patientRepository.findById(patientId).get().getPromotions()
                .forEach(p -> {
                    pharmacies.add(new PharmacyPlainDTO(p.getPharmacy()));
                });
        return pharmacies;
    }

    @Override
    public Boolean setPatientCategory(Long patientId) {
       Patient patient=this.read(patientId).get();
       int patientLoyaltyCount=patient.getLoyaltyCount();

       for(LoyaltyScale loyaltyScale: loyaltyScaleService.read()){
           if(patientLoyaltyCount>=loyaltyScale.getMinPoints() && patientLoyaltyCount<=loyaltyScale.getMaxPoints()){
               patient.setLoyaltyCategory(loyaltyScale.getCategory());
               this.save(patient);
               return true;
           }
       }
       //<regularMin
        LoyaltyScale first = loyaltyScaleService.read().iterator().next();
        if(patientLoyaltyCount<first.getMinPoints()){
            patient.setLoyaltyCategory(LoyaltyCategory.regular);
            this.save(patient);
        }else{
            patient.setLoyaltyCategory(LoyaltyCategory.gold);
            this.save(patient);
        }
       return true;

    }

}
