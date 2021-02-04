package app.service.impl;

import app.dto.MedicationOrderDTO;
import app.model.medication.Medication;
import app.model.medication.MedicationOrder;
import app.model.medication.MedicationQuantity;
import app.model.user.PharmacyAdmin;
import app.repository.MedicationOrderRepository;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicationOrderServiceImpl implements MedicationOrderService {
    private final MedicationOrderRepository medicationOrderRepository;
    private final PharmacyService pharmacyService;
    private final PharmacyAdminService pharmacyAdminService;
    private final MedicationService medicationService;
    private final MedicationQuantityService medicationQuantityService;


    @Autowired
    public MedicationOrderServiceImpl(MedicationOrderRepository medicationOrderRepository, PharmacyService pharmacyService, PharmacyAdminService pharmacyAdminService, MedicationService medicationService, MedicationQuantityService medicationQuantityService) {
        this.medicationOrderRepository = medicationOrderRepository;
        this.pharmacyService = pharmacyService;
        this.pharmacyAdminService = pharmacyAdminService;
        this.medicationService = medicationService;
        this.medicationQuantityService = medicationQuantityService;
    }


    @Override
    public MedicationOrder save(MedicationOrder entity) {
        return medicationOrderRepository.save(entity);
    }

    @Override
    public Collection<MedicationOrder> read() {
        return medicationOrderRepository.findAll();
    }

    @Override
    public Optional<MedicationOrder> read(Long id) {
        return medicationOrderRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        medicationOrderRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return medicationOrderRepository.existsById(id);
    }

    @Override
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

        return this.save(medicationOrder) != null;
    }

    @Override
    public Collection<MedicationOrderDTO> getAllMedicationOrdersByPharmacy(Long pharmacyId) {
        return medicationOrderRepository.getAllMedicationOrdersByPharmacy(pharmacyId).stream()
                .map(medicationOrder -> new MedicationOrderDTO(medicationOrder)).collect(Collectors.toList());
    }

    @Override
    public Collection<MedicationOrderDTO> getMedicationOrderByPharmacyAdmin(Long pharmacyAdminId) { return medicationOrderRepository.getMedicationOrderByPharmacyAdmin(pharmacyAdminId);}
}
