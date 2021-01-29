package app.service.impl;

import app.dto.PharmacyNameIdDTO;
import app.dto.UserPasswordDTO;
import app.model.appointment.Appointment;
import app.model.user.EmployeeType;
import app.model.user.Pharmacist;
import app.repository.PharmacistRepository;
import app.repository.PharmacyRepository;
import app.service.AppointmentService;
import app.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class PharmacistServiceImpl implements PharmacistService {
    private final PharmacistRepository pharmacistRepository;
    private final PharmacyRepository pharmacyRepository;
    private final AppointmentService appointmentService;

    @Autowired
    public PharmacistServiceImpl(PharmacistRepository pharmacistRepository, PharmacyRepository pharmacyRepository, AppointmentService appointmentService) {
        this.pharmacistRepository = pharmacistRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.appointmentService = appointmentService;
    }

    @Override
    public void changePassword(UserPasswordDTO passwordKit) {
        Optional<Pharmacist> _user = pharmacistRepository.findById(passwordKit.getUserId());
        if(_user.isEmpty())
            throw new NullPointerException("User not found");
        Pharmacist user = _user.get();
        validatePassword(passwordKit, user);
        user.getCredentials().setPassword(passwordKit.getNewPassword());
        save(user);
    }



    private void validatePassword(UserPasswordDTO passwordKit, Pharmacist user) {
        String password = user.getCredentials().getPassword();
        if(!password.equals(passwordKit.getOldPassword()))
            throw new IllegalArgumentException("Wrong password");
        else if(!passwordKit.getNewPassword().equals(passwordKit.getRepeatedPassword()))
            throw new IllegalArgumentException("Entered passwords doesn't match");
    }

    @Override
    public Pharmacist save(Pharmacist entity) {
        entity.getWorkingHours().setPharmacy(pharmacyRepository.findById(entity.getWorkingHours().getPharmacy().getId()).get());
        return pharmacistRepository.save(entity);
    }

    @Override
    public Collection<Pharmacist> read() {
        return pharmacistRepository.findAll();
    }

    @Override
    public PharmacyNameIdDTO getPharmacyOfPharmacist(Long id) {
        return new PharmacyNameIdDTO(pharmacistRepository.findById(id).get().getWorkingHours().getPharmacy());
    }

    @Override
    public Optional<Pharmacist> read(Long id) {
        return pharmacistRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        Collection<Appointment> ret = appointmentService.GetAllScheduledAppointmentsByExaminerIdAfterDate(id, EmployeeType.pharmacist, LocalDateTime.now());
        if (ret.size() != 0)
            return;
        Pharmacist pharmacist = this.read(id).get();
        pharmacist.setActive(false);
        pharmacistRepository.save(pharmacist);
    }

    @Override
    public boolean existsById(Long id) {
        return pharmacistRepository.existsById(id);
    }

    @Override
    public Collection<Pharmacist> getPharmacistsByPharmacyId(Long id) {
        ArrayList<Pharmacist> ret = new ArrayList<>();
        for (Pharmacist pharmacist : this.read()) {
            if (pharmacist.getWorkingHours() != null)
                if (pharmacist.getWorkingHours().getPharmacy().getId() == id)
                    ret.add(pharmacist);
        }
        return ret;
    }
}
