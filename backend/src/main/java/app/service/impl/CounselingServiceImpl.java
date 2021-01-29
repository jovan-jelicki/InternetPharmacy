package app.service.impl;

import app.model.appointment.Appointment;
import app.model.time.VacationRequest;
import app.model.time.VacationRequestStatus;
import app.model.user.EmployeeType;
import app.model.user.Pharmacist;
import app.repository.AppointmentRepository;
import app.repository.PharmacistRepository;
import app.repository.VacationRequestRepository;
import app.service.CounselingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class CounselingServiceImpl implements CounselingService {
    private AppointmentRepository appointmentRepository;
    private PharmacistRepository pharmacistRepository;
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    public CounselingServiceImpl(AppointmentRepository appointmentRepository, PharmacistRepository pharmacistRepository,
                                 VacationRequestRepository vacationRequestRepository) {
        this.appointmentRepository = appointmentRepository;
        this.pharmacistRepository = pharmacistRepository;
        this.vacationRequestRepository = vacationRequestRepository;
    }

    @Override
    public Collection<Pharmacist> findAvailablePharmacists(LocalDateTime dateTime) {
        Set<Pharmacist> unavailable = findUnavailable(dateTime);
        Set<Pharmacist> available = findAvailable(dateTime);
        available.removeAll(unavailable);
        return available;
    }

    private Set<Pharmacist> findAvailable(LocalDateTime dateTime) {
        Set<Pharmacist> available = new HashSet<>();
        Collection<Pharmacist> pharmacists = pharmacistRepository.findAll();
        pharmacists.forEach(p -> {
            if(p.isOverlapping(dateTime))
                available.add(p);
        });
        return available;
    }

    private Set<Pharmacist> findUnavailable(LocalDateTime dateTime) {
        Set<Pharmacist> unavailable = new HashSet<>();
        Collection<Appointment> scheduled = appointmentRepository.findAppointmentsByPatientNotNull();
        scheduled.forEach(a -> {
            Pharmacist pharmacist = pharmacistRepository.findById(a.getExaminerId()).get();
            if(a.isOverlapping(dateTime) && isOnVacation(dateTime, a.getExaminerId()))
                unavailable.add(pharmacist);
        });
        return unavailable;
    }

    private boolean isOnVacation(LocalDateTime dateTime, Long id) {
        Collection<VacationRequest> vacations = vacationRequestRepository
                .findByEmployeeIdAndEmployeeTypeAndVacationRequestStatus(id, EmployeeType.dermatologist, VacationRequestStatus.approved);
        for (VacationRequest vacation: vacations) {
            LocalDateTime start = vacation.getPeriod().getPeriodStart();
            LocalDateTime end = vacation.getPeriod().getPeriodEnd();
            if(start.isBefore(dateTime) && end.isAfter(dateTime))
                return true;
        }
        return false;
    }
}
