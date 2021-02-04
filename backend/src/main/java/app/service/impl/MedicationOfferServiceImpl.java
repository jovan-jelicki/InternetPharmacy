package app.service.impl;

import app.dto.MedicationOfferDTO;
import app.model.medication.MedicationOffer;
import app.model.medication.MedicationOrder;
import app.model.user.Supplier;
import app.repository.MedicationOfferRepository;
import app.repository.MedicationOrderRepository;
import app.service.MedicationOfferService;
import app.service.MedicationOrderService;
import app.service.SupplierService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
public class MedicationOfferServiceImpl implements MedicationOfferService {
    private final MedicationOfferRepository medicationOfferRepository;
    private final MedicationOrderService medicationOrderService;

    public MedicationOfferServiceImpl(MedicationOfferRepository medicationOfferRepository, MedicationOrderService medicationOrderService) {
        this.medicationOfferRepository = medicationOfferRepository;
        this.medicationOrderService = medicationOrderService;
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

        return this.save(medicationOffer) !=null;
    }

    //@Override
   // public Collection<MedicationOffer> getMedicationOfferBySupplier(Long supplierId) { return medicationOfferRepository.getMedicationOfferBySupplier(supplierId);}
}
