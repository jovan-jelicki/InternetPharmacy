package app.service.impl;

import app.model.appointment.AppointmentStatus;
import app.model.grade.Grade;
import app.model.grade.GradeType;
import app.model.grade.StrategyName;
import app.model.medication.MedicationReservationStatus;
import app.repository.*;
import app.service.GradingStrategy;
import org.springframework.stereotype.Component;

@Component
public class AssetGradingStrategy implements GradingStrategy {
    private final EPrescriptionRepository ePrescriptionRepository;
    private final MedicationReservationRepository medicationReservationRepository;
    private final AppointmentRepository appointmentRepository;
    private final PharmacyRepository pharmacyRepository;
    private final GradeRepository gradeRepository;

    public AssetGradingStrategy(EPrescriptionRepository ePrescriptionRepository, MedicationReservationRepository medicationReservationRepository, AppointmentRepository appointmentRepository, PharmacyRepository pharmacyRepository, GradeRepository gradeRepository) {
        this.ePrescriptionRepository = ePrescriptionRepository;
        this.medicationReservationRepository = medicationReservationRepository;
        this.appointmentRepository = appointmentRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public Grade grade(Grade grade) {
        Long patientId = grade.getPatient().getId();
        Long gradedId = grade.getGradedId();
        if(grade.getGradeType() == GradeType.medication)
            if(isMedicationInPatientEPrescription(patientId, gradedId) || isMedicationInPatientReservation(patientId, gradedId))
                return gradeRepository.save(grade);
            else
                return null;
        else
            if(hasAttendedAppointments(gradedId, patientId) || hasPatientReservationInPharmacy(gradedId, patientId)) // TODO Add ePrescription checks
                return gradeRepository.save(grade);
            else
                return null;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.asset;
    }

    private boolean isMedicationInPatientReservation(Long patientId, Long medicationId) {
        return medicationReservationRepository
                .findAllByPatient_IdAndStatus(patientId, MedicationReservationStatus.successful)
                .stream()
                .anyMatch(r -> r.getMedicationQuantity().getMedication().getId() == medicationId);
    }

    private boolean isMedicationInPatientEPrescription(Long patientId, Long medicationId) {
        return ePrescriptionRepository
                .findAllByPatient_Id(patientId)
                .stream()
                .anyMatch(p -> p.isMedicationInEPrescription(medicationId));
    }

    private boolean hasAttendedAppointments(Long pharmacyId, Long patientId) {
        return appointmentRepository
                .countAllByPharmacy_IdAndPatient_IdAndAppointmentStatus(
                        pharmacyId, patientId, AppointmentStatus.patientPresent
                ) > 0;
    }

    private boolean hasPatientReservationInPharmacy(Long pharmacyId, Long patientId) {
        return pharmacyRepository
                .findById(pharmacyId).get().getMedicationReservation()
                .stream()
                .anyMatch(r -> r.getPatient().getId() == patientId);
    }


}
