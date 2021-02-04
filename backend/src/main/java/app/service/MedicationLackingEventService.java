package app.service;

import app.dto.MedicationLackingEventDTO;
import app.model.medication.MedicationLackingEvent;

import java.util.Collection;

public interface MedicationLackingEventService extends CRUDService<MedicationLackingEvent> {

    Collection<MedicationLackingEvent> getMedicationLackingEventByPharmacyId(Long pharmacyId);

    Collection<MedicationLackingEventDTO> getMedicationLackingEventListing(Long pharmacyId);
}
