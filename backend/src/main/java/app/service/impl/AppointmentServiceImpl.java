package app.service.impl;

import app.dto.*;
import app.model.appointment.Appointment;
import app.model.appointment.AppointmentStatus;
import app.model.medication.Medication;
import app.model.time.VacationRequest;
import app.model.time.VacationRequestStatus;
import app.model.time.WorkingHours;
import app.model.user.Dermatologist;
import app.model.user.EmployeeType;
import app.model.user.Patient;
import app.repository.AppointmentRepository;
import app.repository.PharmacyRepository;
import app.repository.VacationRequestRepository;
import app.service.AppointmentService;
import app.service.DermatologistService;
import app.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PharmacyRepository pharmacyRepository;

    private final VacationRequestRepository vacationRequestRepository;
    private final PatientService patientService;
    private DermatologistService dermatologistService;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PharmacyRepository pharmacyRepository, DermatologistService dermatologistService, VacationRequestRepository vacationRequestRepository, PatientService patientService ) {
        this.appointmentRepository = appointmentRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.dermatologistService = dermatologistService;
        this.vacationRequestRepository = vacationRequestRepository;
        this.patientService = patientService;
    }

    @PostConstruct
    public void init() {
        dermatologistService.setAppointmentService(this);
    }

    @Override
    public Appointment save(Appointment entity) {
        entity.setPharmacy(pharmacyRepository.findById(entity.getPharmacy().getId()).get());
        return appointmentRepository.save(entity);
    }

    @Override
    public void update(AppointmentUpdateDTO appointmentDTO) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentDTO.getAppointmentId());
//        if(appointment.isEmpty())
//            throw new IllegalArgumentException("Appointment does not exist");
        Optional<Patient> patient = patientService.read(appointmentDTO.getPatientId());
//        if(patient.isEmpty())
//            throw new IllegalArgumentException("Patient does not exits");
        appointment.get().setPatient(patient.get());
        appointmentRepository.save(appointment.get());
    }

    @Override
    public Appointment scheduleCounseling(Appointment entity) {
        LocalDateTime start = entity.getPeriod().getPeriodStart();
        entity.setPatient(patientService.read(entity.getPatient().getId()).get());
        entity.getPeriod().setPeriodEnd(start.plusHours(1));
        return save(entity);
    }

    @Override
    public Collection<Appointment> getAllFinishedByPatientAndExaminerType(Long patientId, EmployeeType type) {
        return appointmentRepository.getAllFinishedByPatientAndExaminerType(patientId, type);
    }

    @Override
    public Appointment cancelCounseling(Long appointmentId) {
        Appointment entity = appointmentRepository.findById(appointmentId).get();
        if(entity.getPeriod().getPeriodStart().minusHours(24).isBefore(LocalDateTime.now()))
            return null;
        entity.setAppointmentStatus(AppointmentStatus.cancelled);
        entity.setActive(false);
        entity.setPatient(patientService.read(entity.getPatient().getId()).get());
        return save(entity);
    }

    @Override
    public Appointment cancelExamination(Long appointmentId) {
        Appointment entity = appointmentRepository.findById(appointmentId).get();
        Appointment newEntity = new Appointment(entity);
        if(entity.getPeriod().getPeriodStart().minusHours(24).isBefore(LocalDateTime.now()))
            return null;
        entity.setAppointmentStatus(AppointmentStatus.cancelled);
        entity.setActive(false);
        entity.setPatient(patientService.read(entity.getPatient().getId()).get());
        save(entity);
        return save(newEntity);
    }

    @Override
    public Collection<Appointment> read() {
        return appointmentRepository.findAll().stream().filter(appointment -> appointment.getActive()).collect(Collectors.toList());
    }

    @Override
    public Collection<Appointment> getAllByExaminerAndAppointmentStatus(Long examinerId, EmployeeType type, AppointmentStatus status){
        return appointmentRepository.getAllByExaminerAndAppointmentStatus(examinerId, type, status);
    }

    public Collection<Appointment> getAllScheduledNotFinishedByExaminer(Long examinerId, EmployeeType type) {
        return appointmentRepository.getAllScheduledNotFinishedByExaminer(examinerId, type);
    }

    @Override
    public Collection<AppointmentScheduledDTO> getAllAppointmentsByExaminer(Long examinerId, EmployeeType type) {
        Collection<AppointmentScheduledDTO> appointmentScheduledDTOS = new ArrayList<>();
        Collection<Appointment> appointments = getAllScheduledNotFinishedByExaminer(examinerId, type);
        for(Appointment a : appointments)
            appointmentScheduledDTOS.add(new AppointmentScheduledDTO(a));

        return appointmentScheduledDTOS;
    }

    @Override
    public Collection<Appointment> getAllNotFinishedByPatientId(Long patientId){
        return appointmentRepository.getAllNotFinishedByPatientId(patientId, AppointmentStatus.available);
    }

    @Override
    public Collection<EventDTO> getAllEventsOfExaminer(Long examinerId, EmployeeType type){
        Collection<Appointment> appointments = getAllByExaminerAndAppointmentStatus(examinerId, type, AppointmentStatus.available);
        Collection<EventDTO> eventDTOS = new ArrayList<>();
        for(Appointment a : appointments){
            eventDTOS.add(new EventDTO(a));
        }
        return eventDTOS;
    }

    @Override
    public Optional<Appointment> read(Long id) {
        Appointment appointment = appointmentRepository.findById(id).get();
        if (appointment.getActive())
            return appointmentRepository.findById(id);
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        Appointment appointment = appointmentRepository.findById(id).get();
        appointment.setActive(false);
        appointmentRepository.save(appointment);
    }

    @Override
    public boolean existsById(Long id) {
        return appointmentRepository.findById(id).get().getActive();
    }

    @Override
    public boolean validateAppointmentTimeRegardingWorkingHours(Appointment entity) {
        WorkingHours workingHoursInPharmacy = dermatologistService.workingHoursInSpecificPharmacy(entity.getExaminerId(), entity.getPharmacy());
        if (workingHoursInPharmacy.getPeriod().getPeriodStart().toLocalTime().minusMinutes(1).isBefore(entity.getPeriod().getPeriodStart().toLocalTime()) &&
                workingHoursInPharmacy.getPeriod().getPeriodEnd().toLocalTime().plusMinutes(1).isAfter(entity.getPeriod().getPeriodEnd().toLocalTime()))
            return true;
        return false;
    }

    @Override
    public boolean validateAppointmentTimeRegardingAllWorkingHours(Appointment entity) {
        boolean ret = true;
        //ArrayList<WorkingHours> allWorkingHours = (ArrayList<WorkingHours>) dermatologistService.read(entity.getExaminerId()).get().getWorkingHours();
        for (WorkingHours workingHours : dermatologistService.read(entity.getExaminerId()).get().getWorkingHours())
            if (!workingHours.getPeriod().getPeriodStart().toLocalTime().minusMinutes(1).isBefore(entity.getPeriod().getPeriodStart().toLocalTime()) &&
                    !workingHours.getPeriod().getPeriodEnd().toLocalTime().plusMinutes(1).isAfter(entity.getPeriod().getPeriodEnd().toLocalTime()))
                ret = false;


        return ret;
    }

    @Override
    public boolean validateAppointmentTimeRegardingVacationRequests(Appointment entity) {
        boolean ret = true;
        for(VacationRequest vacationRequest : vacationRequestRepository.findByEmployeeIdAndEmployeeTypeAndVacationRequestStatus(entity.getExaminerId() ,EmployeeType.ROLE_dermatologist, VacationRequestStatus.approved))
            if (vacationRequest.getPeriod().getPeriodStart().toLocalDate().isBefore(entity.getPeriod().getPeriodStart().toLocalDate()) &&
                    vacationRequest.getPeriod().getPeriodEnd().toLocalDate().isAfter(entity.getPeriod().getPeriodEnd().toLocalDate()))
                return false;
        return ret;
    }

    public boolean validateAppointmentTimeRegardingOtherAppointments(Appointment entity) {
        boolean ret = true;
        for(Appointment appointment : this.getAllAppointmentsByExaminerIdAndType(entity.getExaminerId(), entity.getType())) {
            if (appointment.getPeriod().getPeriodStart().toLocalDate().equals(entity.getPeriod().getPeriodStart().toLocalDate())) {
                if (appointment.getPeriod().getPeriodStart().toLocalTime().minusMinutes(1).isBefore(entity.getPeriod().getPeriodStart().toLocalTime()) &&
                        appointment.getPeriod().getPeriodEnd().toLocalTime().plusMinutes(1).isAfter(entity.getPeriod().getPeriodEnd().toLocalTime())) //A E E A
                    ret = false;
                else if (entity.getPeriod().getPeriodStart().toLocalTime().minusMinutes(1).isBefore(appointment.getPeriod().getPeriodStart().toLocalTime()) &&
                        entity.getPeriod().getPeriodEnd().toLocalTime().plusMinutes(1).isAfter(appointment.getPeriod().getPeriodEnd().toLocalTime())) //E A A E
                    ret = false;
                else if (entity.getPeriod().getPeriodStart().toLocalTime().minusMinutes(1).isBefore(appointment.getPeriod().getPeriodStart().toLocalTime()) &&
                        entity.getPeriod().getPeriodEnd().toLocalTime().minusMinutes(1).isBefore(appointment.getPeriod().getPeriodEnd().toLocalTime()) &&
                        entity.getPeriod().getPeriodEnd().toLocalTime().plusMinutes(1).isAfter(appointment.getPeriod().getPeriodStart().toLocalTime())) //E A E A
                    ret = false;
                else if (appointment.getPeriod().getPeriodStart().toLocalTime().minusMinutes(1).isBefore(entity.getPeriod().getPeriodStart().toLocalTime()) &&
                        appointment.getPeriod().getPeriodEnd().toLocalTime().minusMinutes(1).isBefore(entity.getPeriod().getPeriodEnd().toLocalTime()) &&
                        appointment.getPeriod().getPeriodEnd().toLocalTime().plusMinutes(1).isAfter(entity.getPeriod().getPeriodStart().toLocalTime())) //A E A E
                    ret = false;
            }
        }
        return ret;
    }


    @Override
    public Boolean createAvailableAppointment(Appointment entity) {
        //proveriti da li ima zakazane u tom periodu
        //proveriti da li je na godisnjem
        //proveriti da li tada radi u toj apoteci

        if (!validateAppointmentTimeRegardingWorkingHours(entity))
            return false;
        if (!validateAppointmentTimeRegardingAllWorkingHours(entity))
            return false;
        else if (!validateAppointmentTimeRegardingVacationRequests(entity))
            return false;
        else if (!validateAppointmentTimeRegardingOtherAppointments(entity))
            return false;
        else if (!entity.getPeriod().getPeriodStart().toLocalTime().minusMinutes(1).isBefore(entity.getPeriod().getPeriodEnd().toLocalTime()))
            return false;
        else if (Math.abs(Duration.between(entity.getPeriod().getPeriodEnd(), entity.getPeriod().getPeriodStart()).toMinutes()) > 60 ||
                Math.abs(Duration.between(entity.getPeriod().getPeriodEnd(), entity.getPeriod().getPeriodStart()).toMinutes()) <10)
            return false;

        return this.save(entity) != null;
    }

    @Override
    public Boolean finishAppointment(AppointmentScheduledDTO appointmentScheduledDTO) {
        Appointment appointment = read(appointmentScheduledDTO.getId()).get();
        appointment.setReport(appointmentScheduledDTO.getReport());
        appointment.setAppointmentStatus(AppointmentStatus.patientPresent);
        appointment.setPatient(appointment.getPatient());
        appointment.setPharmacy(appointment.getPharmacy());

        if (appointmentScheduledDTO.getTherapy() != null){
            Collection<Medication> medications = new ArrayList<>();
            medications.add(appointmentScheduledDTO.getTherapy().getMedication());
            if(!patientService.isPatientAllergic(medications,appointmentScheduledDTO.getPatientId())) {
                appointment.setTherapy(appointmentScheduledDTO.getTherapy());
                this.save(appointment);
                return true;
            }else
                throw new IllegalArgumentException("Patient is alergic!!!");
        }
        this.save(appointment);
        return true;
    }

    @Override
    public Collection<Appointment> getAllAppointmentsByExaminerIdAndType(Long examinerId, EmployeeType employeeType) {
        return appointmentRepository.getAllAppointmentsByExaminerIdAndType(examinerId, employeeType);
    }

    @Override
    public Collection<Appointment> GetAllAvailableAppointmentsByPharmacy(Long pharmacyId) {
        return appointmentRepository.GetAllAvailableAppointmentsByPharmacy(pharmacyId);
    }

    @Override
    public Collection<AppointmentFinishedDTO> getFinishedByExaminer(Long examinerId, EmployeeType type) {
        Collection<AppointmentFinishedDTO> retVal = new ArrayList<>();
        for(Appointment a : getAllByExaminerAndAppointmentStatus(examinerId, type, AppointmentStatus.patientPresent)){
            retVal.add(new AppointmentFinishedDTO(a));
        }
        return retVal;
    }

    @Override
    public Collection<Appointment> GetAllScheduledAppointmentsByExaminerIdAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date) {
        return appointmentRepository.GetAllScheduledAppointmentsByExaminerIdAfterDate(examinerId, employeeType,date);
    }

    @Override
    public Collection<Appointment> findAppointmentsByPatientNotNullAndType(EmployeeType type) {
        return appointmentRepository.getAllAvailableByType(type);
    }

    @Override
    public Collection<Appointment> findAppointmentsByPatient_IdAndType(Long id, EmployeeType type) {
        return appointmentRepository.findAppointmentsByPatientAndType(id, type);
    }

    @Override
    public Collection<Appointment> findCancelledByPatientIdAndType(Long id, EmployeeType type) {
        return appointmentRepository.findCancelledByPatientIdAndType(id, type);
    }

    @Override
    public Collection<Appointment> getAllAvailableUpcomingDermatologistAppointmentsByPharmacy(Long pharmacyId) {
        return appointmentRepository.getAllAvailableUpcomingDermatologistAppointmentsByPharmacy(LocalDateTime.now(), pharmacyId);
    }

    @Override
    public Boolean patientDidNotShowUp(Long id) {
        Appointment appointment = read(id).get();
        if(appointment != null){
            if(appointment.getPeriod().getPeriodEnd().isBefore(LocalDateTime.now())) {
                appointment.setAppointmentStatus(AppointmentStatus.patientNotPresent);
                Patient patient = patientService.read(appointment.getPatient().getId()).get();
                patient.setPenaltyCount(patient.getPenaltyCount() + 1);
                patientService.save(patient);
                save(appointment);
                return true;
            }
            throw new IllegalArgumentException("Appointment must pass!");
        }
        throw new IllegalArgumentException("Appointment do not exists!");
    }

    @Override
    public Collection<Appointment> GetAllAvailableAppointmentsByExaminerIdTypeAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date) {
        return appointmentRepository.GetAllAvailableAppointmentsByExaminerIdTypeAfterDate(examinerId,employeeType,date);
    }

    @Override
    public Collection<Appointment> GetAllAvailableAppointmentsByExaminerIdAndPharmacyAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date, Long pharmacyId) {
        return appointmentRepository.GetAllAvailableAppointmentsByExaminerIdAndPharmacyAfterDate(examinerId, employeeType, date, pharmacyId);
    }

    @Override
    public Collection<Appointment> GetAllScheduledAppointmentsByExaminerIdAndPharmacyAfterDate(Long examinerId, EmployeeType employeeType, LocalDateTime date, Long pharmacyId) {
        return appointmentRepository.GetAllScheduledAppointmentsByExaminerIdAndPharmacyAfterDate(examinerId,employeeType,date, pharmacyId);
    }

    private LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


    @Override
    public Collection<Appointment> getSuccessfulAppointmentCountByPeriodAndEmployeeTypeAndPharmacy(LocalDateTime dateStart, LocalDateTime dateEnd, Long pharmacyId, EmployeeType employeeType){
        Collection<Appointment> temp = appointmentRepository.getSuccessfulAppointmentCountByPeriodAndEmployeeTypeAndPharmacy(dateStart, dateEnd, pharmacyId, employeeType);
        return temp;
    }

    @Override
    public Collection<AppointmentEmployeeDTO> getFinishedForComplaint(Long id, EmployeeType type) {
        Collection<Appointment>  finishedAppointments=getAllFinishedByPatientAndExaminerType(id,type);
        ArrayList<Long> dermatologsitIds = new ArrayList<>();
        ArrayList<Long> pharmacistIds = new ArrayList<>();
        ArrayList<AppointmentEmployeeDTO> appointmentEmployeeDTOs = new ArrayList<>();

        for(Appointment appointment : finishedAppointments){
            AppointmentEmployeeDTO appointmentEmployeeDTO= new AppointmentEmployeeDTO();
            if(type.toString()=="dermatologist" && !dermatologsitIds.contains(appointment.getExaminerId())){
                Dermatologist dermatologist=dermatologistService.read(appointment.getExaminerId()).get();
                appointmentEmployeeDTO.setEmployeeId(appointment.getExaminerId());
                appointmentEmployeeDTO.setEmployeeFirstName(dermatologist.getFirstName());
                appointmentEmployeeDTO.setEmployeeLastName(dermatologist.getLastName());
                appointmentEmployeeDTOs.add(appointmentEmployeeDTO);

                dermatologsitIds.add(appointment.getExaminerId());
            }/*else if(type.toString()=="pharmacist"  && !pharmacistIds.contains(appointment.getExaminerId())){
                    Pharmacist pharmacist=pharmacistService.read(appointment.getExaminerId()).get();
                    appointmentEmployeeDTO.setEmployeeId(appointment.getExaminerId());
                    appointmentEmployeeDTO.setEmployeeFirstName(pharmacist.getFirstName());
                    appointmentEmployeeDTO.setEmployeeLastName(pharmacist.getLastName());
                    appointmentEmployeeDTOs.add(appointmentEmployeeDTO);
                    pharmacistIds.add(appointment.getExaminerId());
            }*/
        }
        return appointmentEmployeeDTOs;
    }

    @Override
    public Collection<ReportsDTO> getAppointmentsMonthlyReport(Long pharmacyId) {
        List<LocalDate> allDates = new ArrayList<>();
        String maxDate = LocalDateTime.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        SimpleDateFormat monthDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(monthDate.parse(maxDate));
        }
        catch (Exception e) {
            return null;
        }

        for (int i = 1; i <= 13; i++) {
            allDates.add(convertToLocalDateViaMilisecond(cal.getTime()));
            cal.add(Calendar.MONTH, -1);
        }


        Collections.reverse(allDates);
        System.out.println(allDates);

        ArrayList<ReportsDTO> appointmentCountByMonth = new ArrayList<>();

        for (int i = 0; i < allDates.size()-1; i++) {
            int temp = this.getSuccessfulAppointmentCountByPeriodAndEmployeeTypeAndPharmacy(allDates.get(i).atStartOfDay(), allDates.get(i+1).atStartOfDay(), pharmacyId, EmployeeType.ROLE_dermatologist).size();
            String monthName = allDates.get(i).format(DateTimeFormatter.ofPattern("MMM"));
            appointmentCountByMonth.add(new ReportsDTO(monthName,temp));
        }

        return appointmentCountByMonth;
    }

    @Override
    public Collection<ReportsDTO> getAppointmentsQuarterlyReport(Long pharmacyId) {
        List<LocalDate> allDates = new ArrayList<>();
        String maxDate = LocalDateTime.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        SimpleDateFormat monthDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(monthDate.parse(maxDate));
        }
        catch (Exception e) {
            return null;
        }

        for (int i = 1; i <= 5; i++) {
            allDates.add(convertToLocalDateViaMilisecond(cal.getTime()));
            cal.add(Calendar.MONTH, -3);
        }


        Collections.reverse(allDates);
        System.out.println(allDates);

        ArrayList<ReportsDTO> appointmentCountByQuarter = new ArrayList<>();

        for (int i = 0; i < allDates.size()-1; i++) {
            int temp = this.getSuccessfulAppointmentCountByPeriodAndEmployeeTypeAndPharmacy(allDates.get(i).atStartOfDay(), allDates.get(i+1).atStartOfDay(), pharmacyId, EmployeeType.ROLE_dermatologist).size();
            String monthNameStart = allDates.get(i).format(DateTimeFormatter.ofPattern("MMM"));
            String monthNameEnd = allDates.get(i+1).format(DateTimeFormatter.ofPattern("MMM"));
            appointmentCountByQuarter.add(new ReportsDTO(monthNameStart + "-" + monthNameEnd,temp));
        }

        return appointmentCountByQuarter;
    }

    @Override
    public Collection<ReportsDTO> getAppointmentsYearlyReport(Long pharmacyId) {
        List<LocalDate> allDates = new ArrayList<>();
        String maxDate = LocalDateTime.now().withDayOfMonth(1).withDayOfYear(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        SimpleDateFormat monthDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(monthDate.parse(maxDate));
        }
        catch (Exception e) {
            return null;
        }

        for (int i = 1; i <= 11; i++) {
            allDates.add(convertToLocalDateViaMilisecond(cal.getTime()));
            cal.add(Calendar.MONTH, -12);
        }


        Collections.reverse(allDates);
        System.out.println(allDates);

        ArrayList<ReportsDTO> appointmentCountByYear = new ArrayList<>();

        for (int i = 0; i < allDates.size()-1; i++) {
            int temp = this.getSuccessfulAppointmentCountByPeriodAndEmployeeTypeAndPharmacy(allDates.get(i).atStartOfDay(), allDates.get(i+1).atStartOfDay(), pharmacyId, EmployeeType.ROLE_dermatologist).size();
            String year = allDates.get(i).format(DateTimeFormatter.ofPattern("yyyy"));
            appointmentCountByYear.add(new ReportsDTO(year,temp));
        }

        return appointmentCountByYear;
    }
}