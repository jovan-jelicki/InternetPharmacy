package app.service;

import app.dto.DermatologistDTO;
import app.dto.PharmacyNameIdDTO;
import app.dto.UserPasswordDTO;
import app.model.pharmacy.Pharmacy;
import app.model.time.WorkingHours;
import app.model.user.Dermatologist;

import java.util.Collection;

public interface DermatologistService extends CRUDService<Dermatologist> {
    void changePassword(UserPasswordDTO passwordKit);

    Collection<Dermatologist> getAllDermatologistNotWorkingInPharmacy(Long id);
    Collection<PharmacyNameIdDTO> getPharmacyOfPharmacist(Long id);
    Collection<Dermatologist> getAllDermatologistWorkingInPharmacy(Long id);

    WorkingHours workingHoursInSpecificPharmacy(Long dermatologistId, Pharmacy pharmacy);

    Boolean addDermatologistToPharmacy (DermatologistDTO dermatologistDTO);
    Dermatologist findByEmailAndPassword(String email, String password);

    Boolean deleteDermatologistFromPharmacy(Long pharmacyId, DermatologistDTO dermatologistDTO);

    void setAppointmentService(AppointmentService appointmentService);
}
