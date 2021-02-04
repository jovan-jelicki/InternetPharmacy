package app.service.impl;

import app.dto.MedicationOfferDTO;
import app.model.medication.MedicationOffer;
import app.model.medication.MedicationOrder;
import app.model.user.Supplier;
import app.repository.SupplierRepository;
import app.service.MedicationOfferService;
import app.service.MedicationOrderService;
import app.service.SupplierService;
import org.springframework.stereotype.Service;

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
    public Collection<MedicationOffer> getMedicationOffersBySupplier(Long supplierId) { return supplierRepository.getMedicationOffersBySupplier( supplierId);}


/*
    @Override
    public Boolean createNewMedicationOffer(MedicationOfferDTO medicationOfferDTO) {
        Supplier supplier=this.read(medicationOfferDTO.getSupplierId()).get();

        MedicationOrder medicationOrder=medicationOrderService.read(medicationOfferDTO.getMedicationOrderId()).get();

        MedicationOffer medicationOffer=new MedicationOffer();
        medicationOffer.setCost(medicationOfferDTO.getCost());
        medicationOffer.setShippingDate(medicationOfferDTO.getShippingDate());
        medicationOffer.setStatus(medicationOfferDTO.getStatus());
        medicationOffer.setMedicationOrder(medicationOrder);
        medicationOffer.setId(800L);
        supplier.getMedicationOffer().add(medicationOffer);
        this.save(supplier);

        return medicationOfferService.save(medicationOffer) !=null;
    }
*/
}
