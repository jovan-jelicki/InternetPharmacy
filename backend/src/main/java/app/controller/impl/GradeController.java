package app.controller.impl;

import app.dto.grade.AssetGradeDTO;
import app.dto.grade.EmployeeGradeDTO;
import app.model.grade.Grade;
import app.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/api/grades")
public class GradeController {
    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Void> save(@RequestBody Grade grade) {
        if (gradeService.save(grade) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/dermatologists/{id}")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Collection<EmployeeGradeDTO>> findDermatologistsPatientCanGrade(@PathVariable Long id) {
        return new ResponseEntity<>(gradeService.findDermatologistsPatientCanGrade(id), HttpStatus.OK);
    }

    @GetMapping(value = "/pharmacist/{id}")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Collection<EmployeeGradeDTO>> findPharmacistsPatientCanGrade(@PathVariable Long id) {
        return new ResponseEntity<>(gradeService.findPharmacistPatientCanGrade(id), HttpStatus.OK);
    }

    @GetMapping(value = "/medication/{id}")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Collection<AssetGradeDTO>> findMedicationsPatientCanGrade(@PathVariable Long id) {
        return new ResponseEntity<>(gradeService.findMedicationsPatientCanGrade(id), HttpStatus.OK);
    }

    @GetMapping(value = "/pharmacy/{id}")
    @PreAuthorize("hasRole('patient')")
    public ResponseEntity<Collection<AssetGradeDTO>> findPharmacyPatientCanGrade(@PathVariable Long id) {
        return new ResponseEntity<>(gradeService.findPharmacyPatientCanGrade(id), HttpStatus.OK);
    }
}
