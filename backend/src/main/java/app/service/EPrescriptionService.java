package app.service;

import app.dto.MakeEPrescriptionDTO;
import app.model.medication.EPrescription;

public interface EPrescriptionService extends CRUDService<EPrescription> {
     EPrescription reserveEPrescription(MakeEPrescriptionDTO makeEPrescriptionDTO);

    }
