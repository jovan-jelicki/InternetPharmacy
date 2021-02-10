package app.service;

import app.model.pharmacy.LoyaltyProgram;

public interface LoyaltyProgramService extends CRUDService<LoyaltyProgram>{
    Boolean saveProgram( LoyaltyProgram entity);
}
