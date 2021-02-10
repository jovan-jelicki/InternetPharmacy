package app.service;

import app.dto.EPrescriptionSimpleInfoDTO;
import app.dto.MakeEPrescriptionDTO;
import app.dto.PharmacyQRDTO;
import app.model.medication.EPrescription;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

public interface EPrescriptionService extends CRUDService<EPrescription> {
    EPrescriptionSimpleInfoDTO reserveEPrescription(MakeEPrescriptionDTO makeEPrescriptionDTO);
    Collection<EPrescription> findAllByPatientId(Long patientId);
    Collection<PharmacyQRDTO> getPharmacyForQR( Long ePrescriptionId);
    Boolean buyMedication(Long pharmacyId, Long prescriptionId);
}
