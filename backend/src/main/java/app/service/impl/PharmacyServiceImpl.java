package app.service.impl;

import app.dto.AddMedicationToPharmacyDTO;
import app.dto.PharmacyMedicationListingDTO;
import app.dto.PharmacySearchDTO;
import app.model.medication.*;
import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.repository.PharmacyRepository;
import app.service.MedicationPriceListService;
import app.service.MedicationService;
import app.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private MedicationService medicationService;
    private final MedicationPriceListService medicationPriceListService;

    @Autowired
    public PharmacyServiceImpl(PharmacyRepository pharmacyRepository, MedicationPriceListService medicationPriceListService) {
        this.pharmacyRepository = pharmacyRepository;
        this.medicationPriceListService = medicationPriceListService;
    }

    @Override
    public void setMedicationService(MedicationService medicationService) {
        this.medicationService = medicationService;
    }



    @Override
    public Pharmacy save(Pharmacy entity) {
        return pharmacyRepository.save(entity);
    }

    @Override
    public Collection<Pharmacy> read() {
        return pharmacyRepository.findAll();
    }

    @Override
    public Optional<Pharmacy> read(Long id) {
        return pharmacyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        pharmacyRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return pharmacyRepository.existsById(id);
    }

    @Override
    public Collection<Pharmacy> searchByNameAndAddress(PharmacySearchDTO pharmacySearchDTO) {
        ArrayList<Pharmacy> pharmacies = new ArrayList<>();
        read().forEach(p -> {
            if(p.isEqual(pharmacySearchDTO))
                pharmacies.add(p);
        });
        return pharmacies;
    }

    @Override
    public Boolean checkMedicationQuantity(Collection<MedicationQuantity> medicationQuantities, Pharmacy pharmacy) {
        MedicationQuantity medicationQuantity = new MedicationQuantity();
        for(MedicationQuantity m : pharmacy.getMedicationQuantity()) {
            medicationQuantity =  medicationQuantities.stream().filter(med -> med.getMedication().getId() == m.getMedication().getId()).findFirst().orElse(null);
            if(medicationQuantity != null)
                if (m.getQuantity() - medicationQuantity.getQuantity() < 0)
                     return false;
        }
        return true;
    }

    @Override
    public Boolean addNewMedication(AddMedicationToPharmacyDTO addMedicationToPharmacyDTO) {
        Pharmacy pharmacy = this.read(addMedicationToPharmacyDTO.getPharmacyId()).get();
        //circular dependency
        Medication medication = medicationService.read(addMedicationToPharmacyDTO.getMedicationId()).get();

        //TODO check if pharmacy already has that medication
        if (pharmacy.getMedicationQuantity().stream().filter(medicationQuantity -> medicationQuantity.getMedication().getId() == medication.getId())
                .collect(Collectors.toList()).size() != 0)
            return false;

        pharmacy.getMedicationQuantity().add(new MedicationQuantity(medication, addMedicationToPharmacyDTO.getQuantity()));



        this.save(pharmacy);

        return medicationPriceListService.save(new MedicationPriceList(medication, addMedicationToPharmacyDTO.getCost(),new Period
                (addMedicationToPharmacyDTO.getPriceDateStart(), addMedicationToPharmacyDTO.getPriceDateEnd()), pharmacy)) != null;

    }

    @Override
    public Collection<PharmacyMedicationListingDTO> getPharmacyMedicationListingDTOs(Long pharmacyId) {
        Pharmacy pharmacy = this.read(pharmacyId).get();
        ArrayList<PharmacyMedicationListingDTO> pharmacyMedicationListingDTOS = new ArrayList<PharmacyMedicationListingDTO>();
        for(MedicationQuantity medicationQuantity : pharmacy.getMedicationQuantity()) {
            MedicationPriceList medicationPriceList = medicationPriceListService.GetMedicationPriceInPharmacyByDate(pharmacyId,medicationQuantity.getMedication().getId(), LocalDateTime.now());
            PharmacyMedicationListingDTO pharmacyMedicationListingDTO = new PharmacyMedicationListingDTO(medicationQuantity, medicationPriceList.getCost(), 0, pharmacyId);
            pharmacyMedicationListingDTOS.add(pharmacyMedicationListingDTO); //todo grade
        }
//        pharmacy.getMedicationQuantity().forEach(medicationQuantity -> pharmacyMedicationListingDTOS.add(new PharmacyMedicationListingDTO(medicationQuantity,
//                medicationPriceListService.GetMedicationPriceInPharmacyByDate(pharmacyId,medicationQuantity.getMedication().getId(), LocalDateTime.now()).getCost(),0)));
        return pharmacyMedicationListingDTOS;
    }

    @Override
    public Boolean editMedicationQuantity(PharmacyMedicationListingDTO pharmacyMedicationListingDTO) {
        Pharmacy pharmacy = this.read(pharmacyMedicationListingDTO.getPharmacyId()).get();

        MedicationQuantity medicationQuantity = pharmacy.getMedicationQuantity().stream().
                filter(medicationQuantityPharmacy -> medicationQuantityPharmacy.getId()==pharmacyMedicationListingDTO.getMedicationQuantityId())
                .findFirst().get();

        medicationQuantity.setQuantity(pharmacyMedicationListingDTO.getQuantity());

        return this.save(pharmacy)!= null;
    }

    @Override
    public Boolean deleteMedicationFromPharmacy(PharmacyMedicationListingDTO pharmacyMedicationListingDTO) {
        Pharmacy pharmacy = this.read(pharmacyMedicationListingDTO.getPharmacyId()).get();

        MedicationQuantity medicationQuantity = pharmacy.getMedicationQuantity().stream().
                filter(medicationQuantityPharmacy -> medicationQuantityPharmacy.getId()==pharmacyMedicationListingDTO.getMedicationQuantityId())
                .findFirst().get();

        for (MedicationReservation medicationReservation : pharmacy.getMedicationReservation())
            if (medicationReservation.getMedicationQuantity().getMedication().getId() == pharmacyMedicationListingDTO.getMedicationId()
                && medicationReservation.getStatus() == MedicationReservationStatus.requested)
                return false;


        pharmacy.getMedicationQuantity().remove(medicationQuantity);
        return this.save(pharmacy) != null;
    }


}
