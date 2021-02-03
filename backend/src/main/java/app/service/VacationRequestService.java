package app.service;

import app.dto.VacationRequestDTO;
import app.dto.VacationRequestSendDTO;
import app.model.time.VacationRequest;
import app.model.user.EmployeeType;

import java.util.Collection;

public interface VacationRequestService extends CRUDService<VacationRequest> {
    Collection<VacationRequest> findByPharmacy(Long pharmacyId);

    Collection<VacationRequestDTO> findByPharmacyIdAndEmployeeType(Long pharmacyId, EmployeeType employeeType);

    VacationRequestSendDTO saveVacationRequest(VacationRequestSendDTO entity);

    void confirmVacationRequest(VacationRequestDTO vacationRequestDTO);

    void declineVacationRequest(VacationRequestDTO vacationRequestDTO);

    Collection<VacationRequestDTO> findByEmployeeType(EmployeeType employeeType);


}
