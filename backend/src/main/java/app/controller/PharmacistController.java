package app.controller;

import app.model.time.WorkingHours;
import app.model.user.Pharmacist;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface PharmacistController extends CRUDController<Pharmacist>{
    public ResponseEntity<Collection<WorkingHours>> getPharmacistsWorkingHours(Long id);
}
