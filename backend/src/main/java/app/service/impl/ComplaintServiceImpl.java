package app.service.impl;

import app.dto.ComplaintDTO;
import app.model.complaint.Complaint;
import app.model.pharmacy.Pharmacy;
import app.model.user.Dermatologist;
import app.model.user.Patient;
import app.model.user.Pharmacist;
import app.repository.ComplaintRepository;
import app.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
@Service
public class ComplaintServiceImpl implements ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final DermatologistService dermatologistService;
    private final PharmacistService pharmacistService;
    private final PharmacyService pharmacyService;
    private final PatientService patientService;

    public ComplaintServiceImpl(ComplaintRepository complaintRepository, DermatologistService dermatologistService, PharmacistService pharmacistService, PharmacyService pharmacyService, PatientService patientService) {
        this.complaintRepository = complaintRepository;
        this.dermatologistService = dermatologistService;
        this.pharmacistService = pharmacistService;
        this.pharmacyService = pharmacyService;
        this.patientService = patientService;
    }

    @Transactional(readOnly = false)
    @Override
    public Complaint save(Complaint entity)  {  return complaintRepository.save(entity); }

    @Override
    public Collection<Complaint> read()  {
        return complaintRepository.findAll();
    }

    @Override
    public Optional<Complaint> read(Long id) {
        return complaintRepository.findById(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(Long id)  {
        complaintRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id)  {
        return complaintRepository.existsById(id);
    }

    @Override
    public Collection<ComplaintDTO> getComplaints() {
        ArrayList<ComplaintDTO> complaintDTOS=new ArrayList<>();

        for(Complaint complaint :(List<Complaint>) this.read()){
            complaintDTOS.add(new ComplaintDTO(complaint));
        }

        for(ComplaintDTO complaintDTO : complaintDTOS){
            Patient patient=patientService.read(complaintDTO.getPatientId()).get();
            complaintDTO.setPatientFullName(patient.getFirstName()+" "+patient.getLastName());
            if(complaintDTO.getType().toString()=="dermatologist"){
                Dermatologist dermatologist=dermatologistService.read(complaintDTO.getComplainee_id()).get();
                complaintDTO.setName(dermatologist.getFirstName()+" "+dermatologist.getLastName());
            }else if(complaintDTO.getType().toString()=="pharmacist"){
                Pharmacist pharmacist=pharmacistService.read(complaintDTO.getComplainee_id()).get();
                complaintDTO.setName(pharmacist.getFirstName()+" "+pharmacist.getLastName());
            }else if(complaintDTO.getType().toString()=="pharmacy"){
                Pharmacy pharmacy=pharmacyService.read(complaintDTO.getComplainee_id()).get();
                complaintDTO.setName(pharmacy.getName());
            }
        }

        return complaintDTOS;
    }

    @Transactional(readOnly = false)
    @Override
    public Boolean editComplaint(Long complaintId) {
         Complaint complaint= this.read(complaintId).get();
         complaint.setActive(false);
         this.save(complaint);
         return true;
    }
}
