package app.controller;

import app.model.time.WorkingHours;
import app.model.user.Dermatologist;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface DermatologistController extends CRUDController<Dermatologist> {
    public ResponseEntity<Collection<WorkingHours>> getDermatologistsWorkingHours(long id);
}
