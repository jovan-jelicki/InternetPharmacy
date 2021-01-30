package app.service.impl;

import app.dto.AppointmentScheduledDTO;
import app.dto.EventDTO;
import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.time.VacationRequest;
import app.model.time.VacationRequestStatus;
import app.model.time.WorkingHours;
import app.model.user.EmployeeType;
import app.repository.AppointmentRepository;
import app.repository.PatientRepository;
import app.repository.PharmacyRepository;
import app.repository.VacationRequestRepository;
import app.service.AppointmentService;
import app.service.DermatologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PharmacyRepository pharmacyRepository;
    private final DermatologistService dermatologistService;
    private final VacationRequestRepository vacationRequestRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PharmacyRepository pharmacyRepository, DermatologistService dermatologistService, VacationRequestRepository vacationRequestRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.dermatologistService = dermatologistService;
        this.vacationRequestRepository = vacationRequestRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Appointment save(Appointment entity) {
        entity.setPharmacy(pharmacyRepository.findById(entity.getPharmacy().getId()).get());
        return appointmentRepository.save(entity);
    }

    @Override
    public Appointment scheduleCounseling(Appointment entity) {
        LocalDateTime start = entity.getPeriod().getPeriodStart();
        entity.setPatient(patientRepository.findById(entity.getPatient().getId()).get());
        entity.getPeriod().setPeriodEnd(start.plusHours(1));
        return save(entity);
    }

    @Override
    public Collection<Appointment> read() {
        return appointmentRepository.findAll();
    }

    @Override
    public Collection<Appointment> getAllByExaminerAndAppointmentStatus(Long examinerId, EmployeeType type, AppointmentStatus status){
        return appointmentRepository.getAllByExaminerAndAppointmentStatus(examinerId, type, status);
    }

    public Collection<Appointment> getAllScheduledNotFinishedByExaminer(Long examinerId, EmployeeType type) {
        return appointmentRepository.getAllScheduledNotFinishedByExaminer(examinerId, type);
    }

    @Override
    public Collection<AppointmentScheduledDTO> getAllAppointmentsByExaminer(Long examinerId, EmployeeType type) {
        Collection<AppointmentScheduledDTO> appointmentScheduledDTOS = new ArrayList<>();
        Collection<Appointment> appointments = getAllScheduledNotFinishedByExaminer(examinerId, type);
        for(Appointment a : appointments)
            appointmentScheduledDTOS.add(new AppointmentScheduledDTO(a));

        return appointmentScheduledDTOS;
    }

    @Override
    public Collection<EventDTO> getAllEventsOfExaminer(Long examinerId, EmployeeType type){
        Collection<Appointment> appointments = getAllByExaminerAndAppointmentStatus(examinerId, type, AppointmentStatus.available);
        Collection<EventDTO> eventDTOS = new ArrayList<>();
        for(Appointment a : appointments){
            eventDTOS.add(new EventDTO(a));
        }
        return eventDTOS;
    }

    @Override
    public Optional<Appointment> read(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return appointmentRepository.existsById(id);
    }

    public boolean validateAppointmentTimeRegardingWorkingHours(Appointment entity) {
        WorkingHours workingHoursInPharmacy = dermatologistService.workingHoursInSpecificPharmacy(entity.getExaminerId(), entity.getPharmacy());
        if (workingHoursInPharmacy.getPeriod().getPeriodStart().toLocalTime().isBefore(entity.getPeriod().getPeriodStart().toLocalTime()) &&
            workingHoursInPharmacy.getPeriod().getPeriodEnd().toLocalTime().isAfter(entity.getPeriod().getPeriodEnd().toLocalTime()))
            return true;
        return false;
    }

    public boolean validateAppointmentTimeRegardingAllWorkingHours(Appointment entity) {
        boolean ret = true;
        //ArrayList<WorkingHours> allWorkingHours = (ArrayList<WorkingHours>) dermatologistService.read(entity.getExaminerId()).get().getWorkingHours();
        for (WorkingHours workingHours : dermatologistService.read(entity.getExaminerId()).get().getWorkingHours()) {
            if (!workingHours.getPeriod().getPeriodStart().toLocalTime().isBefore(entity.getPeriod().getPeriodStart().toLocalTime()) &&
                    !workingHours.getPeriod().getPeriodEnd().toLocalTime().isAfter(entity.getPeriod().getPeriodEnd().toLocalTime()))
                ret = false;
        }

        return ret;
    }

    public boolean validateAppointmentTimeRegardingVacationRequests(Appointment entity) {
        boolean ret = true;
        for(VacationRequest vacationRequest : vacationRequestRepository.findByEmployeeIdAndEmployeeTypeAndVacationRequestStatus(entity.getExaminerId() ,EmployeeType.dermatologist, VacationRequestStatus.approved))
            if (vacationRequest.getPeriod().getPeriodStart().toLocalDate().isBefore(entity.getPeriod().getPeriodStart().toLocalDate()) &&
                vacationRequest.getPeriod().getPeriodEnd().toLocalDate().isAfter(entity.getPeriod().getPeriodEnd().toLocalDate()))
                return false;
        return ret;
    }

    public boolean validateAppointmentTimeRegardingOtherAppointments(Appointment entity) {
        boolean ret = true;
        for(Appointment appointment : this.getAllAppointmentsByExaminerIdAndType(entity.getExaminerId(), entity.getType())) {
            if (appointment.getPeriod().getPeriodStart().toLocalDate().equals(entity.getPeriod().getPeriodStart().toLocalDate())) {
                if (appointment.getPeriod().getPeriodStart().toLocalTime().isBefore(entity.getPeriod().getPeriodStart().toLocalTime()) &&
                    appointment.getPeriod().getPeriodEnd().toLocalTime().isAfter(entity.getPeriod().getPeriodEnd().toLocalTime())) //A E E A
                    ret = false;
                else if (entity.getPeriod().getPeriodStart().toLocalTime().isBefore(appointment.getPeriod().getPeriodStart().toLocalTime()) &&
                        entity.getPeriod().getPeriodEnd().toLocalTime().isAfter(appointment.getPeriod().getPeriodEnd().toLocalTime())) //E A A E
                    ret = false;
                else if (entity.getPeriod().getPeriodStart().toLocalTime().isBefore(appointment.getPeriod().getPeriodStart().toLocalTime()) &&
                        entity.getPeriod().getPeriodEnd().toLocalTime().isBefore(appointment.getPeriod().getPeriodEnd().toLocalTime()) &&
                        entity.getPeriod().getPeriodEnd().toLocalTime().isAfter(appointment.getPeriod().getPeriodStart().toLocalTime())) //E A E A
                    ret = false;
                else if (appointment.getPeriod().getPeriodStart().toLocalTime().isBefore(entity.getPeriod().getPeriodStart().toLocalTime()) &&
                        appointment.getPeriod().getPeriodEnd().toLocalTime().isBefore(entity.getPeriod().getPeriodEnd().toLocalTime()) &&
                        appointment.getPeriod().getPeriodEnd().toLocalTime().isAfter(entity.getPeriod().getPeriodStart().toLocalTime())) //A E A E
                    ret = false;
            }
        }
        return ret;
    }


    @Override
    public Boolean createAvailableAppointment(Appointment entity) {
        //proveriti da li ima zakazane u tom periodu
        //proveriti da li je na godisnjem
        //proveriti da li tada radi u toj apoteci

        if (!validateAppointmentTimeRegardingWorkingHours(entity))
            return false;
        if (!validateAppointmentTimeRegardingAllWorkingHours(entity))
            return false;
        else if (!validateAppointmentTimeRegardingVacationRequests(entity))
            return false;
        else if (!validateAppointmentTimeRegardingOtherAppointments(entity))
            return false;
        else if (!entity.getPeriod().getPeriodStart().toLocalTime().isBefore(entity.getPeriod().getPeriodEnd().toLocalTime()))
            return false;

        if (this.save(entity) != null)
            return true;
        return false;
    }

    @Override
    public Collection<Appointment> getAllAppointmentsByExaminerIdAndType(Long examinerId, EmployeeType employeeType) {
        return appointmentRepository.getAllAppointmentsByExaminerIdAndType(examinerId, employeeType);
    }

    @Override
    public Collection<Appointment> GetAllAvailableAppointmentsByPharmacy(Long pharmacyId) {
        return appointmentRepository.GetAllAvailableAppointmentsByPharmacy(pharmacyId);
    }
}
