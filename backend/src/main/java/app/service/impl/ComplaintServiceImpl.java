package app.service.impl;

import app.model.complaint.Complaint;
import app.repository.ComplaintRepository;
import app.service.ComplaintService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    private final ComplaintRepository complaintRepository;

    public ComplaintServiceImpl(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    @Override
    public Complaint save(Complaint entity)  {
        return complaintRepository.save(entity);
    }

    @Override
    public Collection<Complaint> read()  {
        return complaintRepository.findAll();
    }

    @Override
    public Optional<Complaint> read(Long id) {
        return complaintRepository.findById(id);
    }

    @Override
    public void delete(Long id)  {
        complaintRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id)  {
        return complaintRepository.existsById(id);
    }
}
