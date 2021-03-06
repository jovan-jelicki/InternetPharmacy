package app.controller;


import app.dto.DermatologistDTO;
import app.dto.PharmacistDermatologistProfileDTO;
import app.model.pharmacy.Pharmacy;
import app.model.time.Period;
import app.model.time.WorkingHours;
import app.model.user.Address;
import app.model.user.Contact;
import app.model.user.UserType;
import app.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
public class DermatologistControllerTest {

    private static final String URL_PREFIX = "/api/dermatologists";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void set() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    //ukoliko je prazan string znaci da nije approved jer u skripti userima nije setovan isApproved
    //Sto po defaultu ce biti false
    @Test
    @WithMockUser(roles = "dermatologist")
    public void testIsDermatologistAccountApproved() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/isAccountApproved/3"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string(""));
    }



    @Test
    @WithMockUser(roles = "dermatologist")
    @Transactional
    public void testUpdateDermatologist() throws Exception {
       PharmacistDermatologistProfileDTO pharmacistDermatologistProfileDTO = getData();
       String json = TestUtil.json(pharmacistDermatologistProfileDTO);
       mockMvc.perform(put(URL_PREFIX ).contentType(contentType).content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.lastName").value("Periiiic"));
    }

    @WithMockUser(roles = "pharmacyAdmin")
    @Test
    public void testGetAllDermatologistWorkingInPharmacy() throws Exception {
        long pharmacyId = 1L;
        mockMvc.perform(get("/api/dermatologists" + "/getAllDermatologistWorkingInPharmacy/" + pharmacyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].firstName").value(hasItem("Pera")));
    }

    @Test
    @WithMockUser(roles = "pharmacyAdmin")
    public void testAddDermatologistToPharmacy() throws Exception {
        DermatologistDTO dto = new DermatologistDTO();
        dto.setId(4L);
        dto.setFirstName("Agate");
        dto.setLastName("Fendi");
        Contact contact = new Contact();
        contact.setPhoneNumber("+3816954845");
        Address address = new Address();
        address.setStreet("temp");
        address.setTown("temp");
        address.setCountry("SRB");
        address.setLatitude(49D);
        address.setLongitude(19D);
        contact.setAddress(address);
        dto.setContact(contact);
        dto.setGrade(3);

        WorkingHours workingHours = new WorkingHours();
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setId(1L);
        workingHours.setPharmacy(pharmacy);
        Period period = new Period(LocalDateTime.of(2017,1,13,12,0), LocalDateTime.of(2017,1,13,18,0) );
        workingHours.setPeriod(period);

        ArrayList<WorkingHours> workingHoursArrayList = new ArrayList<>();
        workingHoursArrayList.add(workingHours);

        dto.setWorkingHours(workingHoursArrayList);
        dto.setUserType(UserType.ROLE_dermatologist);

        String json = TestUtil.json(dto);

        mockMvc.perform(put(URL_PREFIX + "/addDermatologistToPharmacy").contentType(contentType).content(json))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string(""));
    }



    private PharmacistDermatologistProfileDTO getData() {
        PharmacistDermatologistProfileDTO pharmacistDermatologistProfileDTO = new PharmacistDermatologistProfileDTO();
        pharmacistDermatologistProfileDTO.setFirstName("Pera");
        pharmacistDermatologistProfileDTO.setLastName("Periiiic");
        pharmacistDermatologistProfileDTO.setId(3l);
        pharmacistDermatologistProfileDTO.setUserType(UserType.ROLE_dermatologist);
        pharmacistDermatologistProfileDTO.setEmail("pera.Dermatologist@gmail.com");
        Address address = new Address();
        address.setCountry("Serbia");
        address.setLatitude(213213213.0);
        address.setStreet("Misarska");
        address.setTown("Novi Sad");
        address.setLatitude(213213.0);
        Contact contact = new Contact();
        contact.setAddress(address);
        contact.setPhoneNumber("09999123");
        pharmacistDermatologistProfileDTO.setContact(contact);
        return  pharmacistDermatologistProfileDTO;
    }
}
