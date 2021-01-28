package app.service.impl;

import app.dto.PharmacyNameIdDTO;
import app.dto.UserPasswordDTO;
import app.model.pharmacy.Pharmacy;
import app.model.time.WorkingHours;
import app.model.user.Dermatologist;
import app.model.user.Pharmacist;
import app.repository.DermatologistRepository;
import app.service.DermatologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class DermatologistServiceImpl implements DermatologistService {
    private final DermatologistRepository dermatologistRepository;

    @Autowired
    public DermatologistServiceImpl(DermatologistRepository dermatologistRepository) {
        this.dermatologistRepository = dermatologistRepository;
    }

    @Override
    public void changePassword(UserPasswordDTO passwordKit) {
        Optional<Dermatologist> _user = dermatologistRepository.findById(passwordKit.getUserId());
        if(_user.isEmpty())
            throw new NullPointerException("User not found");
        Dermatologist user = _user.get();
        validatePassword(passwordKit, user);
        user.getCredentials().setPassword(passwordKit.getNewPassword());
        save(user);
    }

    @Override
    public Collection<Dermatologist> getAllDermatologistNotWorkingInPharmacy(Long id) {
        ArrayList<Dermatologist> dermatologistArrayList = new ArrayList<>();
        for (Dermatologist dermatologist : this.read()) {
            if (dermatologist.getWorkingHours().size()!=0) {
                boolean worksInPharmacy = false;
                for (WorkingHours workingHours : dermatologist.getWorkingHours())
                    if (workingHours.getPharmacy().getId() == id) {
                        worksInPharmacy = true;
                        break;
                    }
                if (!worksInPharmacy)
                    dermatologistArrayList.add(dermatologist);
            }
            else
                dermatologistArrayList.add(dermatologist);
        }
        return dermatologistArrayList;
    }

    //query nije radio
    @Override
    public Collection<PharmacyNameIdDTO> getPharmacyOfPharmacist(Long id) {
        ArrayList<Pharmacy> pharmacyList = new ArrayList<>();
        for (WorkingHours w : dermatologistRepository.findById(id).get().getWorkingHours()){
            pharmacyList.add(w.getPharmacy());
        }
        ArrayList<PharmacyNameIdDTO> retVal = new ArrayList<>();
        for(Pharmacy p : pharmacyList){
            retVal.add(new PharmacyNameIdDTO(p));
        }
        return retVal;
    }

    @Override
    public Collection<Dermatologist> getAllDermatologistWorkingInPharmacy(Long id) {
        ArrayList<Dermatologist> dermatologistArrayList = new ArrayList<>();
        for (Dermatologist dermatologist : this.read()) {
            if (dermatologist.getWorkingHours().size()!=0) {
                boolean worksInPharmacy = false;
                for (WorkingHours workingHours : dermatologist.getWorkingHours())
                    if (workingHours.getPharmacy().getId() == id) {
                        worksInPharmacy = true;
                        break;
                    }
                if (worksInPharmacy)
                    dermatologistArrayList.add(dermatologist);
            }
        }
        return dermatologistArrayList;    }

    private void validatePassword(UserPasswordDTO passwordKit, Dermatologist user) {
        String password = user.getCredentials().getPassword();
        if(!password.equals(passwordKit.getOldPassword()))
            throw new IllegalArgumentException("Wrong password");
        else if(!passwordKit.getNewPassword().equals(passwordKit.getRepeatedPassword()))
            throw new IllegalArgumentException("Entered passwords doesn't match");
    }
    @Override
    public Dermatologist save(Dermatologist entity) {
        return dermatologistRepository.save(entity);
    }

    @Override
    public Collection<Dermatologist> read() {
        return dermatologistRepository.findAll();
    }

    @Override
    public Optional<Dermatologist> read(Long id) {
        return dermatologistRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        dermatologistRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return dermatologistRepository.existsById(id);
    }
}
