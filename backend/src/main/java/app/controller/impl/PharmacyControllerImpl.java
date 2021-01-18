package app.controller.impl;

import app.model.pharmacy.Pharmacy;
import app.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/pharmacy")
public class PharmacyControllerImpl {

    private final PharmacyService pharmacyService;

    @Autowired
    public PharmacyControllerImpl(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

//    @GetMapping
//    public ResponseEntity<String> getHello() {
//        return new ResponseEntity<String>("Hi", HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<Collection<Pharmacy>> read() {
        return new ResponseEntity<>(pharmacyService.read(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Pharmacy>> read(@PathVariable Long id) {
        return new ResponseEntity<>(pharmacyService.read(id), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Pharmacy> saveCourse(@RequestBody Pharmacy pharmacy) {
        Pharmacy a = pharmacy;
//        Pha course = new Course();
//        course.setName(courseDTO.getName());
//
//        course = courseService.save(course);
//        return new ResponseEntity<>(new CourseDTO(course), HttpStatus.CREATED);
        return null;
    }
}
