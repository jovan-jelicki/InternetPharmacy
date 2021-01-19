package app.service;

import app.dto.PharmacySearchDTO;
import app.model.pharmacy.Pharmacy;

import java.util.Collection;

public interface PharmacyService extends CRUDService<Pharmacy>{
    Collection<Pharmacy> searchByNameAndAddress(PharmacySearchDTO pharmacySearchDTO);
}
