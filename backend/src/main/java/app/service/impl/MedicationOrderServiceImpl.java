package app.service.impl;

import app.dto.MedicationOrderDTO;
import app.dto.MedicationOrderSupplierDTO;
import app.model.medication.Medication;
import app.model.medication.MedicationOrder;
import app.model.medication.MedicationQuantity;
import app.model.user.PharmacyAdmin;
import app.repository.MedicationOrderRepository;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class MedicationOrderServiceImpl implements MedicationOrderService {
    private final MedicationOrderRepository medicationOrderRepository;
    private final PharmacyService pharmacyService;
    private final PharmacyAdminService pharmacyAdminService;
    private final MedicationService medicationService;
    private final MedicationQuantityService medicationQuantityService;
    private MedicationOfferService medicationOfferService;


    @Autowired
    public MedicationOrderServiceImpl(MedicationOrderRepository medicationOrderRepository, PharmacyService pharmacyService, PharmacyAdminService pharmacyAdminService, MedicationService medicationService, MedicationQuantityService medicationQuantityService) {
        this.medicationOrderRepository = medicationOrderRepository;
        this.pharmacyService = pharmacyService;
        this.pharmacyAdminService = pharmacyAdminService;
        this.medicationService = medicationService;
        this.medicationQuantityService = medicationQuantityService;
    }

    @Override
    public void setMedicationOfferService(MedicationOfferService medicationOfferService) {
        this.medicationOfferService = medicationOfferService;
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean editMedicationOrder(MedicationOrderDTO medicationOrderDTO) {
        MedicationOrder medicationOrder = this.read(medicationOrderDTO.getId()).get();

        if (medicationOfferService.getOffersByOrderId(medicationOrder.getId()).size() != 0)
            return false;

        if (!medicationOrderDTO.getMedicationOrderVersion().equals(medicationOrder.getVersion()))
            throw new ObjectOptimisticLockingFailureException("versions do not match", MedicationOrder.class);

        medicationOrder.setDeadline(medicationOrderDTO.getDeadline());


        ArrayList<MedicationQuantity> medicationQuantities = new ArrayList<>();
        for (MedicationQuantity medicationQuantity : medicationOrderDTO.getMedicationQuantity()) {
            Medication medication = medicationService.getMedicationByName(medicationQuantity.getMedication().getName());
            medicationQuantities.add(medicationQuantityService.save(new MedicationQuantity(medication, medicationQuantity.getQuantity())));
        }

        medicationOrder.setMedicationQuantity(medicationQuantities);

        return this.save(medicationOrder) != null;
    }

    @Override
    public Boolean checkIfOrderIsEditable(Long orderId) {
        return medicationOfferService.getOffersByOrderId(orderId).size() == 0;
    }

    @Override
    public Collection<MedicationOrderSupplierDTO> getAllActive() {
        ArrayList<MedicationOrderSupplierDTO> medicationOrders = new ArrayList<>();
        for(MedicationOrder medicationOrder : this.read()){
            if(medicationOrder.getActive()){
                MedicationOrderSupplierDTO medicationOrderSupplierDTO=new MedicationOrderSupplierDTO();
                medicationOrderSupplierDTO.setMedicationOrderId(medicationOrder.getId());
                medicationOrderSupplierDTO.setMedicationQuantity(medicationOrder.getMedicationQuantity());
                medicationOrderSupplierDTO.setPharmacyName(medicationOrder.getPharmacyAdmin().getPharmacy().getName());
                medicationOrderSupplierDTO.setDeadline(medicationOrder.getDeadline());
                medicationOrderSupplierDTO.setStatus(medicationOrder.getStatus());

                medicationOrders.add(medicationOrderSupplierDTO);
            }
        }
        return medicationOrders;
    }


    @Override
    @Transactional(readOnly = false)
    public MedicationOrder save(MedicationOrder entity) {
        return medicationOrderRepository.save(entity);
    }

    @Override
    public Collection<MedicationOrder> read() {
        return medicationOrderRepository.findAll().stream().filter(medicationOrder -> medicationOrder.getActive()).collect(Collectors.toList());
    }

    @Override
    public Optional<MedicationOrder> read(Long id) {
        MedicationOrder medicationOrder = medicationOrderRepository.findById(id).get();
        if (medicationOrder.getActive())
            return medicationOrderRepository.findById(id);
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        Optional<MedicationOrder> medicationOrderOptional = this.read(id);
        if (!medicationOrderOptional.isPresent())
            return;
        MedicationOrder medicationOrder = medicationOrderOptional.get();
        medicationOrder.setActive(false);
        this.save(medicationOrder);

    }

    @Override
    public boolean existsById(Long id) {
        return this.read(id).isPresent();
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean createNewMedicationOrder(MedicationOrderDTO medicationOrderDTO) {
        PharmacyAdmin pharmacyAdmin = pharmacyAdminService.read(medicationOrderDTO.getPharmacyAdminId()).get();

        MedicationOrder medicationOrder = new MedicationOrder();
        medicationOrder.setDeadline(medicationOrderDTO.getDeadline());
        medicationOrder.setStatus(medicationOrderDTO.getStatus());
        medicationOrder.setPharmacyAdmin(pharmacyAdmin);

        ArrayList<MedicationQuantity> medicationQuantities = new ArrayList<>();
        for (MedicationQuantity medicationQuantity : medicationOrderDTO.getMedicationQuantity()) {
            Medication medication = medicationService.getMedicationByName(medicationQuantity.getMedication().getName());
            medicationQuantities.add(medicationQuantityService.save(new MedicationQuantity(medication, medicationQuantity.getQuantity())));
        }

        medicationOrder.setMedicationQuantity(medicationQuantities);
        medicationOrder.setActive(true);
        return this.save(medicationOrder) != null;
    }

    @Override
    public Collection<MedicationOrderDTO> getAllMedicationOrdersByPharmacy(Long pharmacyId) {
        return medicationOrderRepository.getAllMedicationOrdersByPharmacy(pharmacyId).stream()
                .map(medicationOrder -> new MedicationOrderDTO(medicationOrder)).collect(Collectors.toList());
    }

    @Override
    public Collection<MedicationOrderDTO> getMedicationOrderByPharmacyAdmin(Long pharmacyAdminId) { return medicationOrderRepository.getMedicationOrderByPharmacyAdmin(pharmacyAdminId);}

    @Override
    @Transactional(readOnly = false)
    public Boolean deleteMedicationOrder(Long orderId) {
        if (medicationOfferService.getOffersByOrderId(orderId).size() != 0)
            return false;

        if (!medicationOrderRepository.existsById(orderId))
            return false;

        MedicationOrder medicationOrder = this.read(orderId).get();
        medicationOrder.setActive(false);
        return this.save(medicationOrder) != null;

    }


}
