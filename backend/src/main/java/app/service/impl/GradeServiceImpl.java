package app.service.impl;

import app.dto.grade.AssetGradeDTO;
import app.dto.grade.EmployeeGradeDTO;
import app.model.appointment.AppointmentStatus;
import app.model.grade.Grade;
import app.model.grade.GradeType;
import app.model.medication.Medication;
import app.model.medication.MedicationReservationStatus;
import app.model.pharmacy.Pharmacy;
import app.model.user.Dermatologist;
import app.model.user.EmployeeType;
import app.model.user.Pharmacist;
import app.repository.*;
import app.service.GradeService;
import app.service.GradingStrategy;
import app.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final GradingStrategyFactory gradingStrategyFactory;
    private final PatientService patientService;
    private final DermatologistRepository dermatologistRepository;
    private final PharmacistRepository pharmacistRepository;
    private final PharmacyRepository pharmacyRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicationReservationRepository medicationReservationRepository;
    private final EPrescriptionRepository ePrescriptionRepository;

    private GradingStrategy gradingStrategy;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository, GradingStrategyFactory gradingStrategyFactory, PatientService patientService, DermatologistRepository dermatologistRepository, PharmacistRepository pharmacistRepository, PharmacyRepository pharmacyRepository, AppointmentRepository appointmentRepository, MedicationReservationRepository medicationReservationRepository, EPrescriptionRepository ePrescriptionRepository) {
        this.gradeRepository = gradeRepository;
        this.gradingStrategyFactory = gradingStrategyFactory;
        this.patientService = patientService;
        this.dermatologistRepository = dermatologistRepository;
        this.pharmacistRepository = pharmacistRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicationReservationRepository = medicationReservationRepository;
        this.ePrescriptionRepository = ePrescriptionRepository;
    }

    @Override
    public Grade save(Grade grade) {
//        GradeType gradeType = grade.getGradeType();
//        if(gradeType == GradeType.dermatologist || gradeType == GradeType.pharmacist)
//            gradingStrategy = gradingStrategyFactory.findStrategy(StrategyName.employee);
//        else
//            gradingStrategy = gradingStrategyFactory.findStrategy(StrategyName.asset);
        grade.setPatient(patientService.read(grade.getPatient().getId()).get());
        return gradeRepository.save(grade);
    }

    @Override
    public Collection<EmployeeGradeDTO> findDermatologistsPatientCanGrade(Long patientId) {
        return appointmentRepository
                .getAllByPatient_IdAndAppointmentStatusAndType(patientId, AppointmentStatus.patientPresent, EmployeeType.dermatologist)
                .stream()
                .map(a -> {
                    Dermatologist dermatologist = dermatologistRepository.findById(a.getExaminerId()).get();
                    EmployeeGradeDTO employee = new EmployeeGradeDTO(dermatologist.getId(), dermatologist.getFirstName(), dermatologist.getLastName(), GradeType.dermatologist);
                    Grade grade = gradeRepository.findPatientGradeByGradedId(patientId, a.getExaminerId());
                    if(grade != null) {
                        employee.setGradeId(grade.getId());
                        employee.setGrade(grade.getGrade());
                    }
                    return employee;
                })
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Collection<EmployeeGradeDTO> findPharmacistPatientCanGrade(Long patientId) {
        return appointmentRepository
                .getAllByPatient_IdAndAppointmentStatusAndType(patientId, AppointmentStatus.patientPresent, EmployeeType.pharmacist)
                .stream()
                .map(a -> {
                    Pharmacist pharmacist = pharmacistRepository.findById(a.getExaminerId()).get();
                    EmployeeGradeDTO employee = new EmployeeGradeDTO(pharmacist.getId(), pharmacist.getFirstName(), pharmacist.getLastName(), GradeType.pharmacist);
                    Grade grade = gradeRepository.findPatientGradeByGradedId(patientId, a.getExaminerId());
                    if(grade != null) {
                        employee.setGradeId(grade.getId());
                        employee.setGrade(grade.getGrade());
                    }
                    return employee;
                })
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Collection<AssetGradeDTO> findMedicationsPatientCanGrade(Long patientId) {
        Set<AssetGradeDTO> medications = new HashSet<>();
        medicationReservationRepository
                .findAllByPatient_IdAndStatus(patientId, MedicationReservationStatus.successful)
                .forEach(r -> {
                    Medication medication = r.getMedicationQuantity().getMedication();
                    getMedicationGrade(patientId, medications, medication);
                });
        ePrescriptionRepository
                .findAllByPatient_Id(patientId)
                .forEach(p -> {
                    p.getMedicationQuantity().forEach(m -> {
                        Medication medication = m.getMedication();
                        getMedicationGrade(patientId, medications, medication);
                    });
                });
        return medications;

    }

    public Collection<AssetGradeDTO> findPharmacyPatientCanGrade(Long patientId) {
        Set<AssetGradeDTO> pharmacies = new HashSet<>();
        appointmentRepository
                .getAllByPatient_IdAndAppointmentStatus(patientId, AppointmentStatus.patientPresent)
                .forEach(a -> {
                    Pharmacy pharmacy = a.getPharmacy();
                    getPharmacyGrade(patientId, pharmacies, pharmacy);
                });

        pharmacyRepository
                .findAll()
                .forEach(p -> {
                    if(hasReservationInPharmacy(p, patientId)) {
                        getPharmacyGrade(patientId, pharmacies, p);
                    }
                });

        //TODO EPrescription check

        return pharmacies;
    }

    private void getPharmacyGrade(Long patientId, Set<AssetGradeDTO> pharmacies, Pharmacy pharmacy) {
        AssetGradeDTO asset = new AssetGradeDTO(pharmacy.getId(), pharmacy.getName(), GradeType.pharmacy);
        Grade grade = gradeRepository.findPatientGradeByGradedId(patientId, pharmacy.getId());
        if(grade != null) {
            asset.setGradeId(grade.getId());
            asset.setGrade(grade.getGrade());
        }
        pharmacies.add(asset);
    }

    private boolean hasReservationInPharmacy(Pharmacy pharmacy, Long patientId) {
        return pharmacy
                .getMedicationReservation()
                .stream()
                .anyMatch(r -> r.getPatient().getId() == patientId);
    }

    private void getMedicationGrade(Long patientId, Set<AssetGradeDTO> medications, Medication medication) {
        AssetGradeDTO asset = new AssetGradeDTO(medication.getId(), medication.getName(), GradeType.medication);
        Grade grade = gradeRepository.findPatientGradeByGradedId(patientId, medication.getId());
        if(grade != null) {
            asset.setGradeId(grade.getId());
            asset.setGrade(grade.getGrade());
        }
        medications.add(asset);
    }

    public Grade findPatientGrade(Long patientId, Long gradedId) {
        return gradeRepository.findPatientGradeByGradedId(patientId, gradedId);
    }
    @Override
    public Collection<Grade> findAllByPatientId(Long id) {
        return gradeRepository.findAllByPatient_Id(id);
    }

    @Override
    public double findAverageGradeForEntity(Long id, GradeType gradeType) {
        return gradeRepository.findAverageGradeForEntity(id, gradeType);
    }

    @Override
    public void setGradingStrategy(GradingStrategy gradingStrategy) {

    }
}
