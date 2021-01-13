
package app.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class EPrescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private Date dateIssued;
    @ManyToOne
    @JoinColumn
    private Patient patient;

    @ManyToMany
    private List<MedicationQuantity> medicationQuantity;


}