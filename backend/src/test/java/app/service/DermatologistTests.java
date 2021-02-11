package app.service;

import app.dto.DermatologistDTO;
import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.model.time.WorkingHours;
import app.model.user.Credentials;
import app.model.user.Dermatologist;
import app.model.user.UserType;
import app.repository.DermatologistRepository;
import app.repository.PharmacyRepository;
import app.service.impl.DermatologistServiceImpl;
import app.service.impl.PharmacyServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


//Created by David
@RunWith(SpringRunner.class)
@SpringBootTest
public class DermatologistTests {

    @Mock
    private DermatologistRepository dermatologistRepository;

    @Mock
    private PharmacyRepository pharmacyRepository;

    @Mock
    private PharmacyServiceImpl pharmacyService;

    @InjectMocks
    private DermatologistServiceImpl dermatologistService;


    @Test
    @Transactional
    public void testAddDermatologistToPharmacy(){
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(1L);

        Dermatologist dermatologist = new Dermatologist();
        dermatologist.setFirstName("David");
        dermatologist.setLastName("Drvar");
        dermatologist.setUserType(UserType.ROLE_dermatologist);
        Credentials credentials = new Credentials();
        credentials.setEmail("david@gmail.com");
        dermatologist.setCredentials(credentials);
        dermatologist.setWorkingHours(generateWorkingHours());

        DermatologistDTO dermatologistDTO = new DermatologistDTO();
        dermatologistDTO.setWorkingHours(generateWorkingHours());
        dermatologistDTO.setFirstName("David");
        dermatologistDTO.setLastName("Drvar");
        dermatologistDTO.setUserType(UserType.ROLE_dermatologist);
        dermatologistDTO.setWorkingHours(generateWorkingHours());

        when(dermatologistService.save(dermatologist)).thenReturn(dermatologist);
        when(dermatologistService.read(dermatologistDTO.getId())).thenReturn(java.util.Optional.of(dermatologist));

        assertThat(addDermatologistToPharmacy(dermatologistDTO), is(equalTo(true)));
    }

    private List<WorkingHours> generateWorkingHours() {
        Pharmacy pharmacy1 = new Pharmacy();
        pharmacy1.setId(1L);

        Pharmacy pharmacy2 = new Pharmacy();
        pharmacy2.setId(2L);

        ArrayList<WorkingHours> workingHours = new ArrayList<>();
        WorkingHours workingHours1 = new WorkingHours();
        Period period1 = new Period(LocalDateTime.of(2021,2,16,12,0), LocalDateTime.of(2021,2,16,18,0) );
        workingHours1.setPeriod(period1);
        workingHours1.setPharmacy(pharmacy1);

        WorkingHours workingHours2 = new WorkingHours();
        Period period2 = new Period(LocalDateTime.of(2021,2,16,8,0), LocalDateTime.of(2021,2,16,11,0) );
        workingHours2.setPeriod(period2);
        workingHours2.setPharmacy(pharmacy2);

        workingHours.add(workingHours1);
        workingHours.add(workingHours2);

        return workingHours;
    }

    private Boolean addDermatologistToPharmacy(DermatologistDTO dermatologistDTO) {
        boolean isOverlapping = false;
        for (WorkingHours workingHours : dermatologistDTO.getWorkingHours()) {
            for (WorkingHours workingHoursIsOverlapping : dermatologistDTO.getWorkingHours()) {
                if (validateWorkingHoursRegardingOtherWorkingHours(workingHours, workingHoursIsOverlapping))
                    isOverlapping = true;
            }
        }

        if (isOverlapping)
            return false;

        //validate working hours not in the same pharmacy
        for (int i = 0; i < dermatologistDTO.getWorkingHours().size()-1; i++)
            for (int k = i+1; k < dermatologistDTO.getWorkingHours().size(); k++)
                if(dermatologistDTO.getWorkingHours().get(i).getPharmacy().getId().equals(dermatologistDTO.getWorkingHours().get(k).getPharmacy().getId()))
                    return false;

        Dermatologist dermatologist = convertDTOtoEntity(dermatologistDTO);
        dermatologist.setApprovedAccount(true);
        return dermatologistService.save(dermatologist) != null;
    }

    private boolean validateWorkingHoursRegardingOtherWorkingHours(WorkingHours workingHours, WorkingHours workingHoursIsOverlapping) {
        boolean isOverlapping = false;

        if (workingHours.getPeriod().getPeriodStart().toLocalTime().isBefore(workingHoursIsOverlapping.getPeriod().getPeriodStart().toLocalTime()) &&
                workingHours.getPeriod().getPeriodEnd().toLocalTime().isAfter(workingHoursIsOverlapping.getPeriod().getPeriodEnd().toLocalTime())) //A E E A
            isOverlapping = true;
        else if (workingHoursIsOverlapping.getPeriod().getPeriodStart().toLocalTime().isBefore(workingHours.getPeriod().getPeriodStart().toLocalTime()) &&
                workingHoursIsOverlapping.getPeriod().getPeriodEnd().toLocalTime().isAfter(workingHours.getPeriod().getPeriodEnd().toLocalTime())) //E A A E
            isOverlapping = true;
        else if (workingHoursIsOverlapping.getPeriod().getPeriodStart().toLocalTime().isBefore(workingHours.getPeriod().getPeriodStart().toLocalTime()) &&
                workingHoursIsOverlapping.getPeriod().getPeriodEnd().toLocalTime().isBefore(workingHours.getPeriod().getPeriodEnd().toLocalTime()) &&
                workingHoursIsOverlapping.getPeriod().getPeriodEnd().toLocalTime().isAfter(workingHours.getPeriod().getPeriodStart().toLocalTime())) //E A E A
            isOverlapping = true;
        else if (workingHours.getPeriod().getPeriodStart().toLocalTime().isBefore(workingHoursIsOverlapping.getPeriod().getPeriodStart().toLocalTime()) &&
                workingHours.getPeriod().getPeriodEnd().toLocalTime().isBefore(workingHoursIsOverlapping.getPeriod().getPeriodEnd().toLocalTime()) &&
                workingHours.getPeriod().getPeriodEnd().toLocalTime().isAfter(workingHoursIsOverlapping.getPeriod().getPeriodStart().toLocalTime())) //A E A E
            isOverlapping = true;

        return isOverlapping;
    }

    private Dermatologist convertDTOtoEntity(DermatologistDTO dermatologistDTO) {
        Pharmacy pharmacy1 = new Pharmacy();
        pharmacy1.setId(1L);

        Dermatologist dermatologist = dermatologistService.read(dermatologistDTO.getId()).get();
        dermatologist.setId(dermatologistDTO.getId());
        dermatologist.setWorkingHours(dermatologistDTO.getWorkingHours());
        dermatologist.setFirstName(dermatologistDTO.getFirstName());
        dermatologist.setLastName(dermatologistDTO.getLastName());
        dermatologist.setContact(dermatologistDTO.getContact());

        for (WorkingHours workingHours : dermatologist.getWorkingHours()) {
            when(pharmacyService.read(workingHours.getPharmacy().getId())).thenReturn(java.util.Optional.of(pharmacy1));
            workingHours.setPharmacy(pharmacyService.read(workingHours.getPharmacy().getId()).get());
        }

        return dermatologist;
    }
}
