package app.service.impl;

import app.dto.DermatologistDTO;
import app.dto.PharmacyNameIdDTO;
import app.dto.UserPasswordDTO;
import app.model.appointment.Appointment;
import app.model.pharmacy.Pharmacy;
import app.model.time.WorkingHours;
import app.model.user.Dermatologist;
import app.model.user.EmployeeType;
import app.repository.DermatologistRepository;
import app.service.AppointmentService;
import app.service.DermatologistService;
import app.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class DermatologistServiceImpl implements DermatologistService {
    private final DermatologistRepository dermatologistRepository;
    private final PharmacyService pharmacyService;
    private AppointmentService appointmentService;


    @Autowired
    public DermatologistServiceImpl(DermatologistRepository dermatologistRepository, PharmacyService pharmacyService) {
        this.dermatologistRepository = dermatologistRepository;
        this.pharmacyService = pharmacyService;
        //this.appointmentService = appointmentService;
    }

    @Override
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
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
                    if (workingHours.getPharmacy().getId().equals(id)) {
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
                    if (workingHours.getPharmacy().getId().equals(id)) {
                        worksInPharmacy = true;
                        break;
                    }
                if (worksInPharmacy)
                    dermatologistArrayList.add(dermatologist);
            }
        }
        return dermatologistArrayList;    }

    @Override
    public WorkingHours workingHoursInSpecificPharmacy(Long dermatologistId, Pharmacy pharmacy) {
        Dermatologist dermatologist = dermatologistRepository.findById(dermatologistId).get();
        for (WorkingHours workingHours : dermatologist.getWorkingHours()) {
            if (workingHours.getPharmacy().getId().equals(pharmacy.getId())) {
                return  workingHours;
            }
        }

        return null;
    }


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


    @Override
    public Boolean addDermatologistToPharmacy(DermatologistDTO dermatologistDTO) {
        boolean isOverlapping = false;
        for (WorkingHours workingHours : dermatologistDTO.getWorkingHours()) {
            for (WorkingHours workingHoursIsOverlapping : dermatologistDTO.getWorkingHours()) {
                if (validateWorkingHoursRegardingOtherWorkingHours(workingHours, workingHoursIsOverlapping))
                    isOverlapping = true;
            }
        }

        if (isOverlapping)
            return false;

        //validate working hours not in the same pharmacy
        for (int i = 0; i < dermatologistDTO.getWorkingHours().size()-1; i++)
            for (int k = i+1; k < dermatologistDTO.getWorkingHours().size(); k++)
                if(dermatologistDTO.getWorkingHours().get(i).getPharmacy().getId().equals(dermatologistDTO.getWorkingHours().get(k).getPharmacy().getId()))
                    return false;

        Dermatologist dermatologist = convertDTOtoEntity(dermatologistDTO);
        return this.save(dermatologist) != null;
    }

    private Dermatologist convertDTOtoEntity(DermatologistDTO dermatologistDTO) {
        Dermatologist dermatologist = this.read(dermatologistDTO.getId()).get();
        dermatologist.setId(dermatologistDTO.getId());
        dermatologist.setWorkingHours(dermatologistDTO.getWorkingHours());
        dermatologist.setFirstName(dermatologistDTO.getFirstName());
        dermatologist.setLastName(dermatologistDTO.getLastName());
        dermatologist.setContact(dermatologistDTO.getContact());

        for (WorkingHours workingHours : dermatologist.getWorkingHours())
            workingHours.setPharmacy(pharmacyService.read(workingHours.getPharmacy().getId()).get());


        return dermatologist;
    }


    private boolean validateWorkingHoursRegardingOtherWorkingHours(WorkingHours workingHours, WorkingHours workingHoursIsOverlapping) {
        boolean isOverlapping = false;

        if (workingHours.getPeriod().getPeriodStart().minusMinutes(1).toLocalTime().isBefore(workingHoursIsOverlapping.getPeriod().getPeriodStart().toLocalTime()) &&
                workingHours.getPeriod().getPeriodEnd().toLocalTime().plusMinutes(1).isAfter(workingHoursIsOverlapping.getPeriod().getPeriodEnd().toLocalTime())) //A E E A
            isOverlapping = true;
        else if (workingHoursIsOverlapping.getPeriod().getPeriodStart().toLocalTime().minusMinutes(1).isBefore(workingHours.getPeriod().getPeriodStart().toLocalTime()) &&
                workingHoursIsOverlapping.getPeriod().getPeriodEnd().toLocalTime().plusMinutes(1).isAfter(workingHours.getPeriod().getPeriodEnd().toLocalTime())) //E A A E
            isOverlapping = true;
        else if (workingHoursIsOverlapping.getPeriod().getPeriodStart().toLocalTime().minusMinutes(1).isBefore(workingHours.getPeriod().getPeriodStart().toLocalTime()) &&
                workingHoursIsOverlapping.getPeriod().getPeriodEnd().toLocalTime().minusMinutes(1).isBefore(workingHours.getPeriod().getPeriodEnd().toLocalTime()) &&
                workingHoursIsOverlapping.getPeriod().getPeriodEnd().toLocalTime().plusMinutes(1).isAfter(workingHours.getPeriod().getPeriodStart().toLocalTime())) //E A E A
            isOverlapping = true;
        else if (workingHours.getPeriod().getPeriodStart().toLocalTime().minusMinutes(1).isBefore(workingHoursIsOverlapping.getPeriod().getPeriodStart().toLocalTime()) &&
                workingHours.getPeriod().getPeriodEnd().toLocalTime().minusMinutes(1).isBefore(workingHoursIsOverlapping.getPeriod().getPeriodEnd().toLocalTime()) &&
                workingHours.getPeriod().getPeriodEnd().toLocalTime().plusMinutes(1).isAfter(workingHoursIsOverlapping.getPeriod().getPeriodStart().toLocalTime())) //A E A E
            isOverlapping = true;

        return isOverlapping;
    }

    public Dermatologist findByEmailAndPassword(String email, String password) { return dermatologistRepository.findByEmailAndPassword(email, password);}

    @Override
    public Dermatologist findByEmail(String email) {
        return dermatologistRepository.findByEmail(email);
    }

    public Boolean deleteDermatologistFromPharmacy(Long pharmacyId, DermatologistDTO dermatologistDTO) {
        //check if there are any scheduled appointments for dermatologist in that pharmacy
        Collection<Appointment> scheduledAppointments = appointmentService.
                GetAllScheduledAppointmentsByExaminerIdAndPharmacyAfterDate(dermatologistDTO.getId(), EmployeeType.dermatologist, LocalDateTime.now(), pharmacyId);

        if (scheduledAppointments.size()!=0)
            return false;

        Collection<Appointment> availableAppointments = appointmentService.
                GetAllAvailableAppointmentsByExaminerIdAndPharmacyAfterDate(dermatologistDTO.getId(), EmployeeType.dermatologist, LocalDateTime.now(), pharmacyId);

        //delete available appointment in pharmacy
        for (Appointment appointment : availableAppointments) {
            appointment.setActive(false);
            appointmentService.save(appointment);
        }

        //delete working hour in pharmacy
        Dermatologist dermatologist = this.read(dermatologistDTO.getId()).get();
        //WorkingHours workingHoursPharmacy = (WorkingHours) dermatologist.getWorkingHours().stream().filter(workingHours -> workingHours.getPharmacy().getId()==pharmacyId);

        WorkingHours workingHoursPharmacy = new WorkingHours();
        for (WorkingHours workingHours : dermatologist.getWorkingHours()) {
            if (workingHours.getPharmacy().getId().equals(pharmacyId)) {
                workingHoursPharmacy = workingHours;
                break;
            }
        }

        dermatologist.getWorkingHours().remove(workingHoursPharmacy);
        return this.save(dermatologist)!=null;
    }


}
