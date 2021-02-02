package app.controller.impl;

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
    public ResponseEntity<Void> save(@RequestBody Grade grade) {
        if(gradeService.save(grade) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/patient/{id}")
    public ResponseEntity<Collection<Grade>> findAllByPatientId(@PathVariable Long id) {
        return new ResponseEntity<>(gradeService.findAllByPatientId(id), HttpStatus.OK);
    }
}
