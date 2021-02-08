package app.controller.impl;

import app.dto.MedicationLackingEventDTO;
import app.service.MedicationLackingEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping(value = "api/medicationLacking")
public class MedicationLackingEventController {
    private final MedicationLackingEventService medicationLackingEventService;

    @Autowired
    public MedicationLackingEventController(MedicationLackingEventService medicationLackingEventService) {
        this.medicationLackingEventService = medicationLackingEventService;
    }

    @PreAuthorize("hasAnyRole('pharmacyAdmin')")
    @GetMapping(value = "/getByPharmacyId/{pharmacyId}")
    public ResponseEntity<Collection<MedicationLackingEventDTO>> read(@PathVariable Long pharmacyId) {
        return new ResponseEntity<>(medicationLackingEventService.getMedicationLackingEventListing(pharmacyId), HttpStatus.OK);
    }
}
