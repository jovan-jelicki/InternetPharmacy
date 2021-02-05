package app.service.impl;

import app.dto.MedicationOfferDTO;
import app.model.medication.MedicationOffer;
import app.model.medication.MedicationOfferStatus;
import app.model.medication.MedicationOrder;
import app.model.medication.MedicationQuantity;
import app.model.pharmacy.Pharmacy;
import app.model.user.Supplier;
import app.repository.MedicationOfferRepository;
import app.service.MedicationOfferService;
import app.service.MedicationOrderService;
import app.service.PharmacyService;
import app.service.SupplierService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class MedicationOfferServiceImpl implements MedicationOfferService {
    private final MedicationOfferRepository medicationOfferRepository;
    private final MedicationOrderService medicationOrderService;
    private final SupplierService supplierService;
    private final PharmacyService pharmacyService;

    public MedicationOfferServiceImpl(MedicationOfferRepository medicationOfferRepository, MedicationOrderService medicationOrderService, SupplierService supplierService, PharmacyService pharmacyService) {
        this.medicationOfferRepository = medicationOfferRepository;
        this.medicationOrderService = medicationOrderService;
        this.supplierService = supplierService;
        this.pharmacyService = pharmacyService;
    }

    @PostConstruct
    public void init() {
        medicationOrderService.setMedicationOfferService(this);
    }

    @Override
    public MedicationOffer save(MedicationOffer entity) {
        return medicationOfferRepository.save(entity);
    }
    @Override
    public Collection<MedicationOffer> read()  {
        return medicationOfferRepository.findAll();
    }

    @Override
    public Optional<MedicationOffer> read(Long id)  {
        return medicationOfferRepository.findById(id);
    }

    @Override
    public void delete(Long id)  {
        medicationOfferRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id)  {
        return medicationOfferRepository.existsById(id);
    }

    @Override
    public Boolean createNewMedicationOffer(MedicationOfferDTO medicationOfferDTO) {
        MedicationOrder medicationOrder=medicationOrderService.read(medicationOfferDTO.getMedicationOrderId()).get();

        MedicationOffer medicationOffer=new MedicationOffer();
        medicationOffer.setCost(medicationOfferDTO.getCost());
        medicationOffer.setShippingDate(medicationOfferDTO.getShippingDate());
        medicationOffer.setStatus(medicationOfferDTO.getStatus());
        medicationOffer.setMedicationOrder(medicationOrder);

        this.save(medicationOffer);

        Supplier supplier=supplierService.read(medicationOfferDTO.getSupplierId()).get();
        supplier.getMedicationOffer().add(medicationOffer);

        supplierService.save(supplier);

        return medicationOffer !=null;
    }

    @Override
    public Collection<MedicationOfferDTO> getOffersByOrderId(Long orderId) {
        ArrayList<MedicationOfferDTO> medicationOfferDTOS = new ArrayList<>();
        for (MedicationOffer medicationOffer : medicationOfferRepository.getMedicationOffersByMedicationOrder(orderId)) {
            Supplier supplier = supplierService.getSupplierByMedicationOffer(medicationOffer);
            MedicationOfferDTO medicationOfferDTO = new MedicationOfferDTO(supplier, medicationOffer);
            medicationOfferDTO.setMedicationOrderId(orderId);
            medicationOfferDTOS.add(medicationOfferDTO);
        }
        return medicationOfferDTOS;
    }
    private void updatePharmacyMedicationQuantity(MedicationOffer medicationOffer, MedicationOrder medicationOrder) {
        Pharmacy pharmacy = pharmacyService.read(medicationOrder.getPharmacyAdmin().getPharmacy().getId()).get();
        ArrayList<MedicationQuantity> medicationsToAdd = new ArrayList<>();
        for (MedicationQuantity medicationQuantityOrder : medicationOrder.getMedicationQuantity()) {
            boolean foundMedication = false;
            for (MedicationQuantity medicationQuantityPharmacy : pharmacy.getMedicationQuantity()) {
                if (medicationQuantityOrder.getMedication().getId().equals(medicationQuantityPharmacy.getMedication().getId())) {
                    medicationQuantityPharmacy.setQuantity(medicationQuantityPharmacy.getQuantity() + medicationQuantityOrder.getQuantity());
                    foundMedication = true;
                }
            }
            if (!foundMedication)
                medicationsToAdd.add(medicationQuantityOrder);
        }

        for (MedicationQuantity medicationQuantity : medicationsToAdd)
            pharmacy.getMedicationQuantity().add(new MedicationQuantity(medicationQuantity.getMedication(), medicationQuantity.getQuantity()));

        pharmacyService.save(pharmacy);
    }

    @Override
    public Boolean acceptOffer(MedicationOfferDTO medicationOfferDTO, Long pharmacyAdminId) {
        MedicationOrder medicationOrder = medicationOrderService.read(medicationOfferDTO.getMedicationOrderId()).get();
        if (!medicationOrder.getPharmacyAdmin().getId().equals(pharmacyAdminId))
            return false;

        //TODO uncomment this for final version
//        if (LocalDateTime.now().toLocalDate().isBefore(medicationOrder.getDeadline().toLocalDate()))
//            return false;

        for (MedicationOffer medicationOffer : medicationOfferRepository.getMedicationOffersByMedicationOrder(medicationOfferDTO.getMedicationOrderId())) {
            if (medicationOffer.getId().equals(medicationOfferDTO.getId()) && medicationOffer.getStatus() == MedicationOfferStatus.pending) {
                medicationOffer.setStatus(MedicationOfferStatus.approved);
                this.save(medicationOffer);

                //TODO send confirmation email to supplier

                updatePharmacyMedicationQuantity(medicationOffer, medicationOrder);
                continue;
            }
            medicationOffer.setStatus(MedicationOfferStatus.rejected);
            this.save(medicationOffer);
        }
        return true;
    }



    //@Override
   // public Collection<MedicationOffer> getMedicationOfferBySupplier(Long supplierId) { return medicationOfferRepository.getMedicationOfferBySupplier(supplierId);}
}
