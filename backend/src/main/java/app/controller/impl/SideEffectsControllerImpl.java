package app.controller.impl;

import app.model.medication.MedicationOrder;
import app.model.medication.SideEffect;
import app.service.SideEffectsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping(value = "api/sideEffects")
public class SideEffectsControllerImpl {
    private final SideEffectsService sideEffectsService;

    public SideEffectsControllerImpl(SideEffectsService sideEffectsService) {
        this.sideEffectsService = sideEffectsService;
    }

    @PreAuthorize("hasRole('systemAdmin')")
    @GetMapping(value = "/getAll")
    public ResponseEntity<Collection<SideEffect>> read() {
        return new ResponseEntity<>(sideEffectsService.read(), HttpStatus.OK);
    }
}
