package app.service.impl;

import app.dto.UserPasswordDTO;
import app.model.medication.Ingredient;
import app.model.medication.Medication;
import app.model.user.Patient;
import app.repository.PatientRepository;
import app.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
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

}
