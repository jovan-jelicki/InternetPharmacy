package app.service.impl;

import app.dto.MedicationOfferAndOrderDTO;
import app.dto.MedicationOfferDTO;
import app.dto.MedicationQuantityDTO;
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
    public Collection<MedicationOfferAndOrderDTO> getMedicationOffersBySupplier(Long supplierId){
        ArrayList<MedicationOfferAndOrderDTO> offersAndOrder=new ArrayList<>();
        read().forEach(p -> {
            if (p.getId().equals(supplierId)) {
                for(MedicationOffer m : p.getMedicationOffer()) {
                    MedicationOfferAndOrderDTO offerDTO = new MedicationOfferAndOrderDTO();

                    offerDTO.setCost(m.getCost());
                    offerDTO.setShippingDate(m.getShippingDate());
                    offerDTO.setOfferStatus(m.getStatus());
                    offerDTO.setOfferId(m.getId());

                    MedicationOrder medicationOrder=m.getMedicationOrder();
                    offerDTO.setDeadline(medicationOrder.getDeadline());
                    offerDTO.setMedicationQuantity(medicationOrder.getMedicationQuantity());
                    offerDTO.setOrderStatus(medicationOrder.getStatus());
                    offerDTO.setPharmacyAdminId(medicationOrder.getPharmacyAdmin().getPharmacy().getName());
                    offerDTO.setOrderId(medicationOrder.getId());
                    offersAndOrder.add(offerDTO);
                }
            }});
    return offersAndOrder;
    }

    @Override
    public Collection<MedicationQuantityDTO> getSuppliersMedicationList(Long supplierId) {
        ArrayList<MedicationQuantityDTO> medicationParams = new ArrayList<>();

        for (MedicationQuantity m : read(supplierId).get().getMedicationQuantity()) {
            MedicationQuantityDTO medicationQuantityDTO =new MedicationQuantityDTO();
            medicationQuantityDTO.setMedicationName(m.getMedication().getName());
            medicationQuantityDTO.setMedicationQuantity(m.getQuantity());

            medicationParams.add(medicationQuantityDTO);
        }

        return medicationParams;
    }


}
