package app.service.impl;

import app.model.appointment.Appointment;
import app.model.time.VacationRequest;
import app.model.time.VacationRequestStatus;
import app.model.user.EmployeeType;
import app.model.user.Pharmacist;
import app.repository.VacationRequestRepository;
import app.service.AppointmentService;
import app.service.CounselingService;
import app.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class CounselingServiceImpl implements CounselingService {
    private final AppointmentService appointmentService;
    private final PharmacistService pharmacistService;
    private final VacationRequestRepository vacationRequestRepository;

    @Autowired
    public CounselingServiceImpl(AppointmentService appointmentService, PharmacistService pharmacistService,
                                 VacationRequestRepository vacationRequestRepository) {
        this.appointmentService = appointmentService;
        this.pharmacistService = pharmacistService;
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
        Collection<Pharmacist> pharmacists = pharmacistService.read();
        pharmacists.forEach(p -> {
            if(p.isOverlapping(dateTime) && !isOnVacation(dateTime, p.getId()))
                available.add(p);
        });
        return available;
    }

    private Set<Pharmacist> findUnavailable(LocalDateTime dateTime) {
        Set<Pharmacist> unavailable = new HashSet<>();
        Collection<Appointment> scheduled = appointmentService
                .findAppointmentsByPatientNotNullAndType(EmployeeType.pharmacist);
        scheduled.forEach(a -> {
            Pharmacist pharmacist = pharmacistService.read(a.getExaminerId()).get();
            if(a.isOverlapping(dateTime))
                unavailable.add(pharmacist);
        });
        return unavailable;
    }

    private boolean isOnVacation(LocalDateTime dateTime, Long id) {
        Collection<VacationRequest> vacations = vacationRequestRepository
                .findByEmployeeIdAndEmployeeTypeAndVacationRequestStatus(id, EmployeeType.pharmacist, VacationRequestStatus.approved);
        for (VacationRequest vacation: vacations) {
            LocalDateTime start = vacation.getPeriod().getPeriodStart();
            LocalDateTime end = vacation.getPeriod().getPeriodEnd();
            if(start.isBefore(dateTime) && end.isAfter(dateTime))
                return true;
        }
        return false;
    }
}
