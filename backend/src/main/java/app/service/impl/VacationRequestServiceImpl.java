package app.service.impl;

import app.dto.VacationRequestDTO;
import app.model.time.VacationRequest;
import app.model.time.VacationRequestStatus;
import app.model.user.EmployeeType;
import app.model.user.User;
import app.repository.VacationRequestRepository;
import app.service.DermatologistService;
import app.service.PharmacistService;
import app.service.VacationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class VacationRequestServiceImpl implements VacationRequestService {
    private final VacationRequestRepository vacationRequestRepository;
    private final DermatologistService dermatologistService;
    private final PharmacistService pharmacistService;


    @Autowired
    public VacationRequestServiceImpl(VacationRequestRepository vacationRequestRepository, DermatologistService dermatologistService, PharmacistService pharmacistService) {
        this.vacationRequestRepository = vacationRequestRepository;
        this.dermatologistService = dermatologistService;
        this.pharmacistService = pharmacistService;
    }

    @Override
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
            User user = employeeType == EmployeeType.pharmacist ? pharmacistService.read(vacationRequest.getEmployeeId()).get() : dermatologistService.read(vacationRequest.getEmployeeId()).get();
            VacationRequestDTO vacationRequestDTO = new VacationRequestDTO(user, vacationRequest);
            vacationRequestDTOS.add(vacationRequestDTO);
        }
        return vacationRequestDTOS;
    }

    @Override
    public void confirmVacationRequest(Long id) {
        VacationRequest vacationRequest = this.read(id).get();
        vacationRequest.setVacationRequestStatus(VacationRequestStatus.approved);
        this.save(vacationRequest);
        //TODO send confirmation email
    }

    @Override
    public void declineVacationRequest(VacationRequestDTO vacationRequestDTO) {
        VacationRequest vacationRequest = this.read(vacationRequestDTO.getId()).get();
        vacationRequest.setVacationRequestStatus(VacationRequestStatus.rejected);
        vacationRequest.setRejectionNote(vacationRequestDTO.getRejectionNote());
        this.save(vacationRequest);
        //TODO send rejection email
    }
}
