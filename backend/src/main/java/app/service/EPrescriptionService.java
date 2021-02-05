package app.service;

import app.dto.EPrescriptionSimpleInfoDTO;
import app.dto.MakeEPrescriptionDTO;
import app.dto.MedicationPlainDTO;
import app.model.medication.EPrescription;

import java.util.Collection;

public interface EPrescriptionService extends CRUDService<EPrescription> {
     EPrescriptionSimpleInfoDTO reserveEPrescription(MakeEPrescriptionDTO makeEPrescriptionDTO);
    Collection<EPrescription> findAllByPatientId(Long patientId);

    Collection<MedicationPlainDTO> findAllMedicationsInEPrescriptionByPatientId(Long patientId);
}
