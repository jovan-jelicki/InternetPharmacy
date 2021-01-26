package app.service.impl;

import app.model.appointment.Appointment;
import app.model.user.Pharmacist;
import app.repository.AppointmentRepository;
import app.repository.DermatologistRepository;
import app.repository.PharmacistRepository;
import app.service.CounselingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class CounselingServiceImpl implements CounselingService {
    private AppointmentRepository appointmentRepository;
    private PharmacistRepository pharmacistRepository;

    @Autowired
    public CounselingServiceImpl(AppointmentRepository appointmentRepository, PharmacistRepository pharmacistRepository) {
        this.appointmentRepository = appointmentRepository;
        this.pharmacistRepository = pharmacistRepository;
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
            Pharmacist pharmacist = (Pharmacist) a.getExaminer();
            if(a.isOverlapping(dateTime))
                unavailable.add(pharmacist);
        });
        return unavailable;
    }
}
