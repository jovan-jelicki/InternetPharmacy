package app.service.impl;

import app.dto.MedicationOfferAndOrderDTO;
import app.dto.MedicationSupplierDTO;
import app.dto.UserPasswordDTO;
import app.model.medication.Medication;
import app.model.medication.MedicationOffer;
import app.model.medication.MedicationOrder;
import app.model.medication.MedicationQuantity;
import app.model.user.Supplier;
import app.repository.SupplierRepository;
import app.service.MedicationOrderService;
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

    public SupplierServiceImpl(SupplierRepository supplierRepository, MedicationOrderService medicationOrderService, MedicationService medicationService) {
        this.supplierRepository = supplierRepository;
        this.medicationOrderService = medicationOrderService;
        this.medicationService = medicationService;
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

        supplier.getMedicationQuantity().add(new MedicationQuantity(medication,medicationSupplierDTO.getQuantity()));

        this.save(supplier);

        return true;

    }

    @Override
    public Boolean editSuppliersMedicationQuantity(MedicationSupplierDTO medicationSupplierDTO) {
        Supplier supplier=this.read(medicationSupplierDTO.getSupplierId()).get();

        for(MedicationQuantity medicationQuantity : supplier.getMedicationQuantity()){
            if(medicationQuantity.getId()==medicationSupplierDTO.getMedicationQuantityId()){
                medicationQuantity.setQuantity(medicationSupplierDTO.getQuantity());
            }
        }

         return  this.save(supplier)!=null;
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

    private void validatePassword(UserPasswordDTO passwordKit, Supplier user) {
        String password = user.getCredentials().getPassword();
        if(!password.equals(passwordKit.getOldPassword()))
            throw new IllegalArgumentException("Wrong password");
        else if(!passwordKit.getNewPassword().equals(passwordKit.getRepeatedPassword()))
            throw new IllegalArgumentException("Entered passwords doesn't match");
    }

    @Override
    public void changePassword(UserPasswordDTO passwordKit) {
        Optional<Supplier> _user = supplierRepository.findById(passwordKit.getUserId());
        Supplier user = _user.get();
        validatePassword(passwordKit, user);
        user.getCredentials().setPassword(passwordKit.getNewPassword());
        user.setApprovedAccount(true);
        save(user);
    }



}
