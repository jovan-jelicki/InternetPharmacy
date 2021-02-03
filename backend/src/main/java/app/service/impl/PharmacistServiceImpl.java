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
import java.util.stream.Collectors;

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
        if(_user.isEmpty() || !_user.get().getActive())
            throw new NullPointerException("User not found");
        Pharmacist user = _user.get();
        validatePassword(passwordKit, user);
        user.getCredentials().setPassword(passwordKit.getNewPassword());
        user.setApprovedAccount(true);
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
        entity.setApprovedAccount(true);
        return pharmacistRepository.save(entity);
    }

    @Override
    public Collection<Pharmacist> read() {
        return pharmacistRepository.findAll().stream().filter(pharmacist -> pharmacist.getActive()).collect(Collectors.toList());
    }

    @Override
    public PharmacyNameIdDTO getPharmacyOfPharmacist(Long id) {
        return new PharmacyNameIdDTO(pharmacistRepository.findById(id).filter(pharmacist -> pharmacist.getActive()).get().getWorkingHours().getPharmacy());
    }

    @Override
    public Optional<Pharmacist> read(Long id) {
        Pharmacist pharmacist = pharmacistRepository.findById(id).get();
        if (pharmacist.getActive())
            return pharmacistRepository.findById(id);
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        Collection<Appointment> ret = appointmentService.GetAllScheduledAppointmentsByExaminerIdAfterDate(id, EmployeeType.pharmacist, LocalDateTime.now());
        if (ret.size() != 0)
            return;

        for (Appointment appointment : appointmentService.GetAllAvailableAppointmentsByExaminerIdTypeAfterDate(id, EmployeeType.pharmacist, LocalDateTime.now()))
            appointmentService.delete(appointment.getId());

        Pharmacist pharmacist = this.read(id).get();
        pharmacist.setActive(false);
        pharmacistRepository.save(pharmacist);
    }

    @Override
    public boolean existsById(Long id) {
        Pharmacist pharmacist = pharmacistRepository.findById(id).get();
        return pharmacist.getActive();
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
    public Pharmacist findByEmailAndPassword(String email, String password) { return pharmacistRepository.findByEmailAndPassword(email, password);}

    @Override
    public Pharmacist findByEmail(String email) {
        return pharmacistRepository.findByEmail(email);
    }

}
