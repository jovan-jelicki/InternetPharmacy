package app.service.impl;

import app.dto.VacationRequestDTO;
import app.dto.VacationRequestSendDTO;
import app.model.appointment.Appointment;
import app.model.time.VacationRequest;
import app.model.time.VacationRequestStatus;
import app.model.user.EmployeeType;
import app.model.user.User;
import app.repository.VacationRequestRepository;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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


    @Autowired
    public VacationRequestServiceImpl(PharmacyService pharmacyService, VacationRequestRepository vacationRequestRepository, DermatologistService dermatologistService, PharmacistService pharmacistService, AppointmentService appointmentService) {
        this.vacationRequestRepository = vacationRequestRepository;
        this.pharmacyService = pharmacyService;
        this.dermatologistService = dermatologistService;
        this.pharmacistService = pharmacistService;
        this.appointmentService = appointmentService;
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
    public VacationRequestSendDTO saveVacationRequest(VacationRequestSendDTO entity) {
        VacationRequest vacationRequest = entity.createEntity();
        vacationRequest.setPharmacy(pharmacyService.read(entity.getPharmacy().getId()).get());
        save(vacationRequest);
        return  entity;
    }

    @Override
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
        this.save(vacationRequest);
        //TODO send confirmation email
    }

    @Override
    public void declineVacationRequest(VacationRequestDTO vacationRequestDTO) {
        VacationRequest vacationRequest = this.read(vacationRequestDTO.getId()).get();

        if (!vacationRequestDTO.getVersion().equals(vacationRequest.getVersion()))
            throw new ObjectOptimisticLockingFailureException("versions do not match", VacationRequest.class);

        vacationRequest.setVacationRequestStatus(VacationRequestStatus.rejected);
        vacationRequest.setRejectionNote(vacationRequestDTO.getRejectionNote());
        this.save(vacationRequest);
        //TODO send rejection email
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
