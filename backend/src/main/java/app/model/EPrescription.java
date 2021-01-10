
package app.model;

import java.util.Date;
import java.util.List;

public class EPrescription {

    private String code;
    private Date dateIssued;
    private Long patientId;
    private List<MedicationQuantity> medicationQuantity;


}