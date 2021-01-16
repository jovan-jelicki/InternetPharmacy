package app.controller;

import app.model.Dermatologist;
import app.model.WorkingHours;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface DermatologistController extends CRUDController<Dermatologist> {
    public ResponseEntity<Collection<WorkingHours>> getDermatologistsWorkingHours(long id);
}