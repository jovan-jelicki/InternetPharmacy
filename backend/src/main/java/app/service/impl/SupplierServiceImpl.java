package app.service.impl;

import app.dto.MedicationOfferAndOrderDTO;
import app.dto.MedicationQuantityDTO;
import app.dto.MedicationSupplierDTO;
import app.model.medication.Medication;
import app.model.medication.MedicationOffer;
import app.model.medication.MedicationOrder;
import app.model.medication.MedicationQuantity;
import app.model.user.Supplier;
import app.repository.SupplierRepository;
import app.service.MedicationOrderService;
import app.service.MedicationQuantityService;
import app.service.MedicationService;
import app.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService{
    private SupplierRepository supplierRepository;
    private final MedicationOrderService medicationOrderService;
    private  final MedicationService medicationService;
    private final MedicationQuantityService medicationQuantityService;

    public SupplierServiceImpl(SupplierRepository supplierRepository, MedicationOrderService medicationOrderService, MedicationService medicationService, MedicationQuantityService medicationQuantityService) {
        this.supplierRepository = supplierRepository;
        this.medicationOrderService = medicationOrderService;
        this.medicationService = medicationService;
        this.medicationQuantityService = medicationQuantityService;
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
    public Collection<MedicationQuantity> getSuppliersMedicationList(Long supplierId) {
        ArrayList<MedicationQuantity> medicationParams = new ArrayList<>();

        for (MedicationQuantity m : read(supplierId).get().getMedicationQuantity()) {
            medicationParams.add(m);
        }

        return medicationParams;
    }

    @Override
    public Collection<Medication> getNonMedicationsBySupplier(Long supplierId) {
        Set<Medication> supplierMedications = new HashSet<>();
        Set<Medication> allMedications = new HashSet<Medication>(medicationService.read());
        for (MedicationQuantity medicationQuantity : read(supplierId).get().getMedicationQuantity()){
            supplierMedications.add(medicationQuantity.getMedication());
        }
        allMedications.removeAll(supplierMedications);
        return allMedications;
    }

    @Override
    public Boolean addNewMedication(MedicationSupplierDTO medicationSupplierDTO) {
        Supplier supplier=this.read(medicationSupplierDTO.getSupplierId()).get();
        Medication medication = medicationService.read(medicationSupplierDTO.getMedicationId()).get();

        if (supplier.getMedicationQuantity().stream().filter(medicationQuantity -> medicationQuantity.getMedication().getId().equals(medication.getId()))
                .collect(Collectors.toList()).size() != 0)
            return false;

        MedicationQuantity medicationQuantity=new MedicationQuantity(medication,medicationSupplierDTO.getQuantity());

        medicationQuantityService.save(medicationQuantity);
        supplier.getMedicationQuantity().add(medicationQuantity);

        this.save(supplier);

        return true;

    }

    @Override
    public Boolean editSuppliersMedicationQuantity(MedicationSupplierDTO medicationSupplierDTO) {
        MedicationQuantity medicationQuantity=medicationQuantityService.read(medicationSupplierDTO.getMedicationQuantityId()).get();
        int quantity=medicationSupplierDTO.getQuantity();
        if(quantity<=0){
            medicationQuantityService.delete(medicationSupplierDTO.getMedicationQuantityId());
            return null;
        }
        medicationQuantity.setQuantity(quantity);
        medicationQuantityService.save(medicationQuantity);


         return  medicationQuantityService.save(medicationQuantity)!=null;
    }

    @Override
    public Boolean deleteMedicationQuantity(MedicationSupplierDTO medicationSupplierDTO) {
        Supplier supplier=this.read(medicationSupplierDTO.getSupplierId()).get();
        for(MedicationQuantity medicationQuantity :  supplier.getMedicationQuantity()){
            if(medicationQuantity.getId().equals(medicationSupplierDTO.getMedicationQuantityId())){
                supplier.getMedicationQuantity().remove(medicationQuantity);
                break;
            }
        }
        return this.save(supplier)!=null;

    }

    @Override
    public Supplier getSupplierByMedicationOffer(MedicationOffer medicationOffer) {
        for(Supplier supplier : this.read())
            if (supplier.getMedicationOffer().contains(medicationOffer))
                return  supplier;

        return null;
    }


}
