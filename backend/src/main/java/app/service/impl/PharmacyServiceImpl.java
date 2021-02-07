package app.service.impl;

import app.dto.*;
import app.model.grade.GradeType;
import app.model.medication.*;
import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.model.user.EmployeeType;
import app.repository.AppointmentRepository;
import app.repository.PharmacyRepository;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private MedicationService medicationService;
    private MedicationPriceListService medicationPriceListService;
    private final AppointmentRepository appointmentRepository;
    private PromotionService promotionService;
    private final GradeService gradeService;
    private final PharmacyAdminService pharmacyAdminService;
    private MedicationOfferService medicationOfferService;

    @Autowired
    public PharmacyServiceImpl(PharmacyRepository pharmacyRepository, AppointmentRepository appointmentRepository, GradeService gradeService, PharmacyAdminService pharmacyAdminService) {
        this.pharmacyRepository = pharmacyRepository;
        this.appointmentRepository = appointmentRepository;
        this.gradeService = gradeService;
        this.pharmacyAdminService = pharmacyAdminService;
    }

    @Override
    public void setPromotionService(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @Override
    public void setMedicationOffer(MedicationOfferService medicationOfferService) {
        this.medicationOfferService = medicationOfferService;
    }

    @Override
    public Pharmacy savePharmacy(PharmacyAdminPharmacyDTO pharmacyAdminPharmacyDTO) {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName(pharmacyAdminPharmacyDTO.getName());
        pharmacy.setDescription(pharmacyAdminPharmacyDTO.getDescription());
        pharmacy.setAddress(pharmacyAdminPharmacyDTO.getAddress());

        this.save(pharmacy);

        return pharmacy;
    }



    @Override
    public void setMedicationService(MedicationService medicationService) {
        this.medicationService = medicationService;
    }


    @Override
    public void setMedicationPriceListService(MedicationPriceListServiceImpl medicationPriceListService) {
        this.medicationPriceListService = medicationPriceListService;
    }

    @Override
    public Collection<PharmacyMedicationDTO> getPharmacyByMedication(Long medicationId) {
        ArrayList<PharmacyMedicationDTO> pharmacies = new ArrayList<>();
        read().forEach(p -> {
            for (MedicationQuantity q : p.getMedicationQuantity()) {
                if (q.getMedication().getId() == medicationId) {
                    PharmacyMedicationDTO pmDTO = new PharmacyMedicationDTO();
                    pmDTO.setId(p.getId());
                    pmDTO.setName(p.getName());
                    pmDTO.setAddress(p.getAddress());
                    pmDTO.setMedicationId(medicationId);

                    double cena = medicationPriceListService.getMedicationPrice(p.getId(), medicationId);
                    pmDTO.setMedicationPrice(cena);
                    pharmacies.add(pmDTO);
                }
            }
        });
        return pharmacies;
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
            if (p.isEqual(pharmacySearchDTO))
                pharmacies.add(p);
        });
        return pharmacies;
    }

    @Override
    public Boolean checkMedicationQuantity(Collection<MedicationQuantity> medicationQuantities, Pharmacy pharmacy) {
        MedicationQuantity medicationQuantity = new MedicationQuantity();
        for (MedicationQuantity m : pharmacy.getMedicationQuantity()) {
            medicationQuantity = medicationQuantities.stream().filter(med -> med.getMedication().getId() == m.getMedication().getId()).findFirst().orElse(null);
            if (medicationQuantity != null)
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
        if (pharmacy.getMedicationQuantity().stream().filter(medicationQuantity -> medicationQuantity.getMedication().getId().equals(medication.getId()))
                .collect(Collectors.toList()).size() != 0)
            return false;

        pharmacy.getMedicationQuantity().add(new MedicationQuantity(medication, addMedicationToPharmacyDTO.getQuantity()));


        this.save(pharmacy);

        return medicationPriceListService.save(new MedicationPriceList(medication, addMedicationToPharmacyDTO.getCost(), new Period
                (addMedicationToPharmacyDTO.getPriceDateStart(), addMedicationToPharmacyDTO.getPriceDateEnd()), pharmacy)) != null;

    }

    @Override
    public Collection<PharmacyMedicationListingDTO> getPharmacyMedicationListingDTOs(Long pharmacyId) {
        Pharmacy pharmacy = this.read(pharmacyId).get();
        ArrayList<PharmacyMedicationListingDTO> pharmacyMedicationListingDTOS = new ArrayList<PharmacyMedicationListingDTO>();
        for (MedicationQuantity medicationQuantity : pharmacy.getMedicationQuantity()) {
            double cost = medicationPriceListService.GetMedicationPriceInPharmacyByDate(pharmacyId, medicationQuantity.getMedication().getId(), LocalDateTime.now());
            PharmacyMedicationListingDTO pharmacyMedicationListingDTO =
                    new PharmacyMedicationListingDTO(medicationQuantity, cost, gradeService.
                            findAverageGradeForEntity(medicationQuantity.getMedication().getId(), GradeType.medication), pharmacyId);
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
                filter(medicationQuantityPharmacy -> medicationQuantityPharmacy.getId().equals(pharmacyMedicationListingDTO.getMedicationQuantityId()))
                .findFirst().get();

        medicationQuantity.setQuantity(pharmacyMedicationListingDTO.getQuantity());

        return this.save(pharmacy) != null;
    }

    // izbrisem aspirin, dodam aspirin pa pokusam opet da izbrisem
    @Override
    public Boolean deleteMedicationFromPharmacy(PharmacyMedicationListingDTO pharmacyMedicationListingDTO) {
        Pharmacy pharmacy = this.read(pharmacyMedicationListingDTO.getPharmacyId()).get();

        MedicationQuantity medicationQuantity = new MedicationQuantity();
        for (MedicationQuantity medicationQuantityFilter : pharmacy.getMedicationQuantity()) {
            if (medicationQuantityFilter.getId().equals(pharmacyMedicationListingDTO.getMedicationQuantityId())) {
                medicationQuantity = medicationQuantityFilter;
                break;
            }
        }
        Medication medication = medicationService.read(pharmacyMedicationListingDTO.getMedicationId()).get();

        //check if medication is for reservation
        for (MedicationReservation medicationReservation : pharmacy.getMedicationReservation())
            if (medicationReservation.getMedicationQuantity().getMedication().getId().equals(pharmacyMedicationListingDTO.getMedicationId())
                    && medicationReservation.getStatus() == MedicationReservationStatus.requested)
                return false;

        //check if medication is in any current pharmacy promotions
        if (promotionService.getCurrentPromotionsByPharmacyAndDate(pharmacy.getId(), LocalDateTime.now()).stream().filter(promotion ->
                promotion.getMedicationsOnPromotion().contains(medication)).count() != 0)
            return false;

        pharmacy.getMedicationQuantity().remove(medicationQuantity);
        return this.save(pharmacy) != null;
    }

    private LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private int filterMedicationReservationsByPeriod(LocalDateTime periodStart, LocalDateTime periodEnd, Pharmacy pharmacy) {
        int temp = 0;
        for (MedicationReservation medicationReservation : pharmacy.getMedicationReservation())
            if (medicationReservation.getPickUpDate().isAfter(periodStart) && medicationReservation.getPickUpDate().isBefore(periodEnd))
                temp += medicationReservation.getMedicationQuantity().getQuantity();

        return temp;
    }

    @Override
    public Collection<ReportsDTO> getMedicationsConsumptionMonthlyReport(Long pharmacyId) {
        List<LocalDate> allDates = new ArrayList<>();
        String maxDate = LocalDateTime.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        SimpleDateFormat monthDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(monthDate.parse(maxDate));
        } catch (Exception e) {
            return null;
        }

        for (int i = 1; i <= 13; i++) {
            allDates.add(convertToLocalDateViaMilisecond(cal.getTime()));
            cal.add(Calendar.MONTH, -1);
        }

        Collections.reverse(allDates);
        System.out.println(allDates);

        Pharmacy pharmacy = this.read(pharmacyId).get();

        ArrayList<ReportsDTO> medicationConsumptionByMonth = new ArrayList<>();


        for (int i = 0; i < allDates.size() - 1; i++) {
            int temp = this.filterMedicationReservationsByPeriod(allDates.get(i).atStartOfDay(), allDates.get(i + 1).atStartOfDay(), pharmacy);
            String monthName = allDates.get(i).format(DateTimeFormatter.ofPattern("MMM"));
            medicationConsumptionByMonth.add(new ReportsDTO(monthName, temp));
        }
        return medicationConsumptionByMonth;
    }

    @Override
    public Collection<ReportsDTO> getMedicationsConsumptionQuarterlyReport(Long pharmacyId) {
        List<LocalDate> allDates = new ArrayList<>();
        String maxDate = LocalDateTime.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        SimpleDateFormat monthDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(monthDate.parse(maxDate));
        } catch (Exception e) {
            return null;
        }

        for (int i = 1; i <= 5; i++) {
            allDates.add(convertToLocalDateViaMilisecond(cal.getTime()));
            cal.add(Calendar.MONTH, -3);
        }

        Collections.reverse(allDates);
        System.out.println(allDates);

        Pharmacy pharmacy = this.read(pharmacyId).get();

        ArrayList<ReportsDTO> medicationConsumptionByMQuarter = new ArrayList<>();

        for (int i = 0; i < allDates.size() - 1; i++) {
            int temp = this.filterMedicationReservationsByPeriod(allDates.get(i).atStartOfDay(), allDates.get(i + 1).atStartOfDay(), pharmacy);
            String monthNameStart = allDates.get(i).format(DateTimeFormatter.ofPattern("MMM"));
            String monthNameEnd = allDates.get(i + 1).format(DateTimeFormatter.ofPattern("MMM"));
            medicationConsumptionByMQuarter.add(new ReportsDTO(monthNameStart + "-" + monthNameEnd, temp));
        }
        return medicationConsumptionByMQuarter;
    }

    @Override
    public Collection<ReportsDTO> getMedicationsConsumptionYearlyReport(Long pharmacyId) {
        List<LocalDate> allDates = new ArrayList<>();
        String maxDate = LocalDateTime.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        SimpleDateFormat monthDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(monthDate.parse(maxDate));
        } catch (Exception e) {
            return null;
        }

        for (int i = 1; i <= 11; i++) {
            allDates.add(convertToLocalDateViaMilisecond(cal.getTime()));
            cal.add(Calendar.MONTH, -12);
        }

        Collections.reverse(allDates);
        System.out.println(allDates);

        Pharmacy pharmacy = this.read(pharmacyId).get();

        ArrayList<ReportsDTO> medicationConsumptionByMQuarter = new ArrayList<>();

        for (int i = 0; i < allDates.size() - 1; i++) {
            int temp = this.filterMedicationReservationsByPeriod(allDates.get(i).atStartOfDay(), allDates.get(i + 1).atStartOfDay(), pharmacy);
            String year = allDates.get(i).format(DateTimeFormatter.ofPattern("yyyy"));
            medicationConsumptionByMQuarter.add(new ReportsDTO(year, temp));
        }
        return medicationConsumptionByMQuarter;
    }


    @Override
    public Collection<ReportIncomeDTO> getPharmacyIncomeReportByPeriod(LocalDateTime periodStart, LocalDateTime periodEnd, Long pharmacyId) {

        //uspesne rezervacije lekova - obratiti paznju na pricelist u tom periodu
        //uspesni appointmenti dermatologa i farmaceuta
        //eprescription
        //medication orders

        Pharmacy pharmacy = this.read(pharmacyId).get();

        LocalDateTime start = periodStart;
        LocalDateTime end = periodEnd;
        List<LocalDateTime> totalDates = new ArrayList<>();
        while (!start.isAfter(end)) {
            totalDates.add(start);
            start = start.plusDays(1);
        }

        System.out.println(totalDates);

        ArrayList<ReportIncomeDTO> reportsDTOS = new ArrayList<>();

        for (int i = 0; i < totalDates.size() - 1; i++) {
            LocalDateTime dayStart = totalDates.get(i);
            LocalDateTime dayEnd = totalDates.get(i + 1);
            dayStart.with(LocalTime.of(0, 0));
            dayStart.with(LocalTime.of(0, 0));
            double income = 0;
            double expense = 0;
            income += appointmentRepository.getSuccessfulAppointmentCountByPeriodAndEmployeeTypeAndPharmacy(dayStart, dayEnd, pharmacyId, EmployeeType.dermatologist)
                    .size() * pharmacy.getDermatologistCost();
            income += appointmentRepository.getSuccessfulAppointmentCountByPeriodAndEmployeeTypeAndPharmacy(dayStart, dayEnd, pharmacyId, EmployeeType.pharmacist)
                    .size() * pharmacy.getPharmacistCost();

            ArrayList<MedicationReservation> medicationReservations = (ArrayList<MedicationReservation>) pharmacy.getMedicationReservation().stream().filter(medicationReservation -> medicationReservation.getPickUpDate().toLocalDate().isEqual(dayStart.toLocalDate()))
                    .collect(Collectors.toList());
            for (MedicationReservation medicationReservation : medicationReservations) {
                double medicationPrice = medicationPriceListService.GetMedicationPriceInPharmacyByDate(pharmacyId, medicationReservation.getMedicationQuantity().getMedication().getId(), dayEnd);
                if (medicationReservation.getDiscounted())
                    income += medicationReservation.getMedicationQuantity().getQuantity() * medicationPrice / 2;
                income += medicationReservation.getMedicationQuantity().getQuantity() * medicationPrice;
            }

            for (MedicationOffer medicationOffer : medicationOfferService.getApprovedMedicationOffersByPharmacyAndPeriod(pharmacyId, dayStart, dayEnd))
                expense += medicationOffer.getCost();

            reportsDTOS.add(new ReportIncomeDTO(dayStart.toLocalDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy")), income, expense));
        }


        return reportsDTOS;
    }

}