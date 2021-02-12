package app.service.impl;

import app.dto.MedicationOfferAndOrderDTO;
import app.dto.MedicationOfferDTO;
import app.model.medication.*;
import app.model.pharmacy.Pharmacy;
import app.model.user.Supplier;
import app.repository.MedicationOfferRepository;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class MedicationOfferServiceImpl implements MedicationOfferService {
    private final MedicationOfferRepository medicationOfferRepository;
    private final MedicationOrderService medicationOrderService;
    private final SupplierService supplierService;
    private final PharmacyService pharmacyService;
    private final EmailService emailService;

    @Autowired
    public MedicationOfferServiceImpl(MedicationOfferRepository medicationOfferRepository, MedicationOrderService medicationOrderService, SupplierService supplierService, PharmacyService pharmacyService, EmailService emailService) {
        this.medicationOfferRepository = medicationOfferRepository;
        this.medicationOrderService = medicationOrderService;
        this.supplierService = supplierService;
        this.pharmacyService = pharmacyService;
        this.emailService = emailService;
    }

    @PostConstruct
    public void init() {
        medicationOrderService.setMedicationOfferService(this);
        pharmacyService.setMedicationOffer(this);
    }

    @Override
    @Transactional(readOnly = false)
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
    @Transactional(readOnly = false)
    public void delete(Long id)  {
        medicationOfferRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id)  {
        return medicationOfferRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = false)
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
    @Transactional(readOnly = false)
    public Boolean editMedicationOffer(MedicationOfferAndOrderDTO medicationOffer) {
        MedicationOffer medOffer=read(medicationOffer.getOfferId()).get();
        medOffer.setCost(medicationOffer.getCost());
        medOffer.setShippingDate(medicationOffer.getShippingDate());
        this.save(medOffer);

        return medOffer!=null;
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

    private void updatePharmacyMedicationQuantity(MedicationOrder medicationOrder) {
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

    private void updateSupplierMedicationQuantity(MedicationOrder medicationOrder, MedicationOffer medicationOffer) {
        Supplier supplier = supplierService.getSupplierByMedicationOffer(medicationOffer);
        for (MedicationQuantity medicationQuantitySupplier : supplier.getMedicationQuantity())
            for (MedicationQuantity medicationQuantityOrder : medicationOrder.getMedicationQuantity())
                if (medicationQuantityOrder.getMedication().getId().equals(medicationQuantitySupplier.getMedication().getId()))
                    medicationQuantitySupplier.setQuantity(medicationQuantitySupplier.getQuantity() - medicationQuantityOrder.getQuantity());

        supplierService.save(supplier);
    }

    private void sendConfirmationEmailToSupplier(MedicationOffer medicationOffer, String pharmacyName) {
        Supplier supplier = supplierService.getSupplierByMedicationOffer(medicationOffer);
        String emailBody = "Dear " + supplier.getFirstName() + " " + supplier.getLastName() + ", \nwe are pleased to inform you " +
                "that your offer for pharmacy " + pharmacyName + " has been confirmed."  + "\n" +
                "\nSincerely, WebPharm.";
        String email = "david.drvar.bogdanovic@gmail.com";
        emailService.sendMail(email, "Confirmation mail", emailBody);
    }

    private void sendRejectionEmailToSupplier(MedicationOffer medicationOffer, String pharmacyName) {
        Supplier supplier = supplierService.getSupplierByMedicationOffer(medicationOffer);
        String emailBody = "Dear " + supplier.getFirstName() + " " + supplier.getLastName() + ", \n we are sorry to inform you" +
                "that your offer for pharmacy " + pharmacyName + " has been rejected."  + "\n" +
                "\nSincerely, WebPharm.";
        String email = "david.drvar.bogdanovic@gmail.com";
        emailService.sendMail(email, "Rejection mail", emailBody);
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean acceptOffer(MedicationOfferDTO medicationOfferDTO, Long pharmacyAdminId) {
        MedicationOrder medicationOrder = medicationOrderService.read(medicationOfferDTO.getMedicationOrderId()).get();
        if (!medicationOrder.getPharmacyAdmin().getId().equals(pharmacyAdminId))
            return false;


        if (LocalDateTime.now().toLocalDate().isBefore(medicationOrder.getDeadline().toLocalDate()))
            return false;

        if (!medicationOrder.getVersion().equals(medicationOfferDTO.getMedicationOrderVersion()))
            throw new ObjectOptimisticLockingFailureException("versions do not match", MedicationOrder.class);


        for (MedicationOffer medicationOffer : medicationOfferRepository.getMedicationOffersByMedicationOrder(medicationOfferDTO.getMedicationOrderId())) {
            if (medicationOffer.getId().equals(medicationOfferDTO.getId()) && medicationOffer.getStatus() == MedicationOfferStatus.pending) {

                medicationOffer.setStatus(MedicationOfferStatus.approved);
                if (!medicationOffer.getVersion().equals(medicationOfferDTO.getMedicationOfferVersion()))
                    throw new ObjectOptimisticLockingFailureException("versions do not match", MedicationOffer.class);

                this.save(medicationOffer);

                sendConfirmationEmailToSupplier(medicationOffer, medicationOrder.getPharmacyAdmin().getPharmacy().getName());

                updatePharmacyMedicationQuantity(medicationOrder);
                updateSupplierMedicationQuantity(medicationOrder, medicationOffer);
                continue;
            }
            medicationOffer.setStatus(MedicationOfferStatus.rejected);
            sendRejectionEmailToSupplier(medicationOffer, medicationOrder.getPharmacyAdmin().getPharmacy().getName());
            this.save(medicationOffer);
        }
        medicationOrder.setStatus(MedicationOrderStatus.processed);
        return medicationOrderService.save(medicationOrder) != null;
    }

    @Override
    public Collection<MedicationOffer> getApprovedMedicationOffersByPharmacyAndPeriod(Long pharmacyId, LocalDateTime periodStart, LocalDateTime periodEnd) {
        return medicationOfferRepository.getApprovedMedicationOffersByPharmacyAndPeriod(pharmacyId, periodStart, periodEnd);
    }



    //@Override
   // public Collection<MedicationOffer> getMedicationOfferBySupplier(Long supplierId) { return medicationOfferRepository.getMedicationOfferBySupplier(supplierId);}
}
