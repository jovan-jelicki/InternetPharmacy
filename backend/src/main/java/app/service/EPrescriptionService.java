package app.service;

import app.dto.EPrescriptionSimpleInfoDTO;
import app.dto.MakeEPrescriptionDTO;
import app.model.medication.EPrescription;

public interface EPrescriptionService extends CRUDService<EPrescription> {
     EPrescriptionSimpleInfoDTO reserveEPrescription(MakeEPrescriptionDTO makeEPrescriptionDTO);

    }
