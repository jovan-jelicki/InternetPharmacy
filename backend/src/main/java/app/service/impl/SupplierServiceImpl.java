package app.service.impl;

import app.dto.MedicationOfferDTO;
import app.model.medication.MedicationOffer;
import app.model.medication.MedicationOrder;
import app.model.medication.MedicationQuantity;
import app.model.user.Supplier;
import app.repository.SupplierRepository;
import app.service.MedicationOfferService;
import app.service.MedicationOrderService;
import app.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService{
    private SupplierRepository supplierRepository;
    private final MedicationOrderService medicationOrderService;


    public SupplierServiceImpl(SupplierRepository supplierRepository, MedicationOrderService medicationOrderService) {
        this.supplierRepository = supplierRepository;
        this.medicationOrderService = medicationOrderService;
    }

    @Override
    public Supplier save(Supplier entity)  {
        return supplierRepository.save(entity);
    }

    @Override
    public Collection<Supplier> read()  {
        return supplierRepository.findAll();
    }

    @Override
    public Optional<Supplier> read(Long id){return supplierRepository.findById(id); }
    @Override
    public void delete(Long id) {supplierRepository.deleteById(id);}

    @Override
    public boolean existsById(Long id)  {
        return supplierRepository.existsById(id);
    }

    @Override
    public Supplier findByEmailAndPassword(String email, String password) { return supplierRepository.findByEmailAndPassword(email, password);}

    @Override
    public Supplier findByEmail(String email) {
        return supplierRepository.findByEmail(email);
    }

    @Override
    public Collection<MedicationOfferDTO> getMedicationOffersBySupplier(Long supplierId){
        ArrayList<MedicationOfferDTO> offers=new ArrayList<>();
        read().forEach(p -> {
            if (p.getId().equals(supplierId)) {
                for(MedicationOffer m : p.getMedicationOffer()) {
                    MedicationOfferDTO offerDTO = new MedicationOfferDTO();

                    offerDTO.setId(m.getId());
                    offerDTO.setCost(m.getCost());
                    offerDTO.setShippingDate(m.getShippingDate());
                    offerDTO.setMedicationOrderId(m.getMedicationOrder().getId());
                    offerDTO.setSupplierId(supplierId);

                    offers.add(offerDTO);
                }
            }});
    return offers;
    }

}
