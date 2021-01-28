package app.service.impl;

import app.dto.EventDTO;
import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.user.EmployeeType;
import app.repository.AppointmentRepository;
import app.repository.PharmacyRepository;
import app.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PharmacyRepository pharmacyRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PharmacyRepository pharmacyRepository) {
        this.appointmentRepository = appointmentRepository;
        this.pharmacyRepository = pharmacyRepository;
    }

    @Override
    public Appointment save(Appointment entity) {
        entity.setPharmacy(pharmacyRepository.findById(entity.getPharmacy().getId()).get());
        return appointmentRepository.save(entity);
    }

    @Override
    public Collection<Appointment> read() {
        return appointmentRepository.findAll();
    }

    @Override
    public Collection<Appointment> getAllByExaminerAndAppointmentStatus(Long examinerId, EmployeeType type, AppointmentStatus status){
        return appointmentRepository.getAllByExaminerAndAppointmentStatus(examinerId, type, status);
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
}
