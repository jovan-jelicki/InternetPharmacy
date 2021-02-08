package app.service;

import app.model.pharmacy.LoyaltyProgram;
import app.model.pharmacy.LoyaltyScale;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoyaltyProgramService extends CRUDService<LoyaltyProgram>{
    Boolean saveProgram( LoyaltyProgram entity);
}
