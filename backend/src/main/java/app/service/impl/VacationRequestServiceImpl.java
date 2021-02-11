package app.service.impl;

import app.dto.VacationRequestDTO;
import app.dto.VacationRequestSendDTO;
import app.model.appointment.Appointment;
import app.model.time.VacationRequest;
import app.model.time.VacationRequestStatus;
import app.model.user.Dermatologist;
import app.model.user.EmployeeType;
import app.model.user.Pharmacist;
import app.model.user.User;
import app.repository.VacationRequestRepository;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class VacationRequestServiceImpl implements VacationRequestService {
    private final VacationRequestRepository vacationRequestRepository;
    private final DermatologistService dermatologistService;
    private final PharmacistService pharmacistService;
    private final PharmacyService pharmacyService;
    private final AppointmentService appointmentService;
    private final EmailService emailService;


    @Autowired
    public VacationRequestServiceImpl(PharmacyService pharmacyService, VacationRequestRepository vacationRequestRepository, DermatologistService dermatologistService, PharmacistService pharmacistService, AppointmentService appointmentService, EmailService emailService) {
        this.vacationRequestRepository = vacationRequestRepository;
        this.pharmacyService = pharmacyService;
        this.dermatologistService = dermatologistService;
        this.pharmacistService = pharmacistService;
        this.appointmentService = appointmentService;
        this.emailService = emailService;
    }

    @Override
    @Transactional(readOnly = false)
    public VacationRequest save(VacationRequest entity) {
        return vacationRequestRepository.save(entity);
    }

    @Override
    public Collection<VacationRequest> read() {
        return vacationRequestRepository.findAll();
    }

    @Override
    public Optional<VacationRequest> read(Long id) {
        return vacationRequestRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        vacationRequestRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return vacationRequestRepository.existsById(id);
    }

    @Override
    public Collection<VacationRequest> findByPharmacy(Long pharmacyId) {
        return vacationRequestRepository.findByPharmacyId(pharmacyId);
    }

    @Override
    public Collection<VacationRequestDTO> findByPharmacyIdAndEmployeeType(Long pharmacyId, EmployeeType employeeType) {
        ArrayList<VacationRequestDTO> vacationRequestDTOS = new ArrayList<>();
        for (VacationRequest vacationRequest : vacationRequestRepository.findByPharmacyIdAndEmployeeType(pharmacyId, employeeType)) {
            User user = employeeType == EmployeeType.ROLE_pharmacist ? pharmacistService.read(vacationRequest.getEmployeeId()).get() : dermatologistService.read(vacationRequest.getEmployeeId()).get();
            VacationRequestDTO vacationRequestDTO = new VacationRequestDTO(user, vacationRequest);
            vacationRequestDTOS.add(vacationRequestDTO);
        }
        return vacationRequestDTOS;
    }

    @Override
    @Transactional(readOnly = false)
    public VacationRequestSendDTO saveVacationRequest(VacationRequestSendDTO entity) {
        VacationRequest vacationRequest = entity.createEntity();
        vacationRequest.setPharmacy(pharmacyService.read(entity.getPharmacy().getId()).get());
        save(vacationRequest);
        return  entity;
    }

    private void sendEmailToEmployee(VacationRequest vacationRequest, VacationRequestStatus vacationRequestStatus) {
        String emailBody = "";
        String subject = vacationRequestStatus == VacationRequestStatus.approved ? "Confirmation email" : "Rejection email";
        if (vacationRequest.getEmployeeType() == EmployeeType.ROLE_pharmacist)
        {
            Pharmacist pharmacist = pharmacistService.read(vacationRequest.getEmployeeId()).get();
            emailBody = vacationRequestStatus == VacationRequestStatus.approved ? "Dear " + pharmacist.getFirstName() + " " + pharmacist.getLastName() + ", \nwe are pleased to inform you " +
                    "that your vacation request for pharmacy " + pharmacist.getWorkingHours().getPharmacy().getName() + " in period " +
                    vacationRequest.getPeriod().getPeriodStart().format(DateTimeFormatter.ISO_LOCAL_DATE) + " " +
                    vacationRequest.getPeriod().getPeriodEnd().format(DateTimeFormatter.ISO_LOCAL_DATE) + " has been confirmed."  + "\n" +
                    "\nSincerely, WebPharm."
                    : "Dear " + pharmacist.getFirstName() + " " + pharmacist.getLastName() + ", \nwe are sorry to inform you " +
                    "that your vacation request for pharmacy " + pharmacist.getWorkingHours().getPharmacy().getName() + " in period " +
                    vacationRequest.getPeriod().getPeriodStart().format(DateTimeFormatter.ISO_LOCAL_DATE) + " " +
                    vacationRequest.getPeriod().getPeriodEnd().format(DateTimeFormatter.ISO_LOCAL_DATE) + " has been rejected. " +
                    "The reason for the rejection of your vacation request is the following: " + vacationRequest.getRejectionNote() + "\n" +
                    "\nSincerely, WebPharm.";
        }
        else {
            Dermatologist dermatologist = dermatologistService.read(vacationRequest.getEmployeeId()).get();
            emailBody = vacationRequestStatus == VacationRequestStatus.approved ? "Dear " + dermatologist.getFirstName() + " " + dermatologist.getLastName() + ", \nwe are pleased to inform you " +
                    "that your vacation request in period " + vacationRequest.getPeriod().getPeriodStart().format(DateTimeFormatter.ISO_LOCAL_DATE) + " " +
                    vacationRequest.getPeriod().getPeriodEnd().format(DateTimeFormatter.ISO_LOCAL_DATE) + " has been confirmed."  + "\n" +
                    "\nSincerely, WebPharm."
                    : "Dear " + dermatologist.getFirstName() + " " + dermatologist.getLastName() + ", \nwe are sorry to inform you " +
                    "that your vacation request in period " + vacationRequest.getPeriod().getPeriodStart().format(DateTimeFormatter.ISO_LOCAL_DATE) + " " +
                    vacationRequest.getPeriod().getPeriodEnd().format(DateTimeFormatter.ISO_LOCAL_DATE) + " has been rejected."  + "\n" +
                    "\nSincerely, WebPharm.";
        }

        String email = "david.drvar.bogdanovic@gmail.com";
        emailService.sendMail(email, subject, emailBody);


    }

    @Override
    @Transactional(readOnly = false)
    public void confirmVacationRequest(VacationRequestDTO vacationRequestDTO) {
        VacationRequest vacationRequest = this.read(vacationRequestDTO.getId()).get();

        if (!vacationRequestDTO.getVersion().equals(vacationRequest.getVersion()))
            throw new ObjectOptimisticLockingFailureException("versions do not match", VacationRequest.class);

        //check if there are any scheduled pharmacist appointments for that period
        Collection<Appointment> scheduledAppointmentsForVacationRequestPeriod = appointmentService.
                GetAllScheduledAppointmentsByExaminerIdAfterDate(vacationRequestDTO.getEmployeeId(),vacationRequestDTO.getEmployeeType(), vacationRequestDTO.getPeriod().getPeriodStart())
                .stream().filter(appointment -> appointment.getPeriod().getPeriodEnd().isBefore(vacationRequestDTO.getPeriod().getPeriodEnd())).collect(Collectors.toList());

        if (scheduledAppointmentsForVacationRequestPeriod.size() != 0)
            return;

        //delete available appointments for that period
        Collection<Appointment> availableAppointmentsForVacationRequestPeriod = appointmentService.
                GetAllAvailableAppointmentsByExaminerIdTypeAfterDate(vacationRequestDTO.getEmployeeId(),vacationRequestDTO.getEmployeeType(), vacationRequestDTO.getPeriod().getPeriodStart())
                .stream().filter(appointment -> appointment.getPeriod().getPeriodEnd().isBefore(vacationRequestDTO.getPeriod().getPeriodEnd())).collect(Collectors.toList());

        for (Appointment appointment : availableAppointmentsForVacationRequestPeriod) {
            appointment.setActive(false);
            appointmentService.save(appointment);
        }

        vacationRequest.setVacationRequestStatus(VacationRequestStatus.approved);
        if (this.save(vacationRequest) != null)
            sendEmailToEmployee(vacationRequest, vacationRequest.getVacationRequestStatus());
    }

    @Override
    @Transactional(readOnly = false)
    public void declineVacationRequest(VacationRequestDTO vacationRequestDTO) {
        VacationRequest vacationRequest = this.read(vacationRequestDTO.getId()).get();

        if (!vacationRequestDTO.getVersion().equals(vacationRequest.getVersion()))
            throw new ObjectOptimisticLockingFailureException("versions do not match", VacationRequest.class);

        vacationRequest.setVacationRequestStatus(VacationRequestStatus.rejected);
        vacationRequest.setRejectionNote(vacationRequestDTO.getRejectionNote());
        if (this.save(vacationRequest) != null)
            sendEmailToEmployee(vacationRequest, vacationRequest.getVacationRequestStatus());
    }

    @Override
    public Collection<VacationRequestDTO> findByEmployeeType(EmployeeType employeeType) {
        ArrayList<VacationRequestDTO> vacationRequestDTOS = new ArrayList<>();
        for (VacationRequest vacationRequest : vacationRequestRepository.findByEmployeeType(employeeType)) {
            User user = employeeType==EmployeeType.ROLE_dermatologist ? dermatologistService.read(vacationRequest.getEmployeeId()).get() : pharmacistService.read(vacationRequest.getEmployeeId()).get();
            vacationRequestDTOS.add(new VacationRequestDTO(user, vacationRequest));
        }
        return vacationRequestDTOS;
    }
}
