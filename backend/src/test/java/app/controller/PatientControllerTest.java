package app.controller;

import app.dto.PatientDTO;
import app.dto.PharmacistDermatologistProfileDTO;
import app.dto.UserPasswordDTO;
import app.model.user.Address;
import app.model.user.Contact;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientControllerTest {
    private static final String URL_PREFIX = "/api/patients";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void set() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(roles = "patient")
    @Transactional
    public void patientUpdateTest() throws Exception {
        PatientDTO patientDTO = getPatient();
        String json = TestUtil.json(patientDTO);
        mockMvc.perform(put(URL_PREFIX).contentType(contentType).content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.lastName").value("Brdar"));
    }

    @Test
    @WithMockUser(roles = "patient")
    public void patientChangePasswordTest_ValidPassword() throws Exception {
        UserPasswordDTO userPasswordDTO = getUserPassword_Valid();
        String json = TestUtil.json(userPasswordDTO);
        mockMvc.perform(put(URL_PREFIX + "/pass").contentType(contentType).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "patient")
    public void patientChangePasswordTest_InvalidPassword() throws Exception {
        UserPasswordDTO userPasswordDTO = getUserPassword_Invalid();
        String json = TestUtil.json(userPasswordDTO);
        mockMvc.perform(put(URL_PREFIX + "/pass").contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }

    private UserPasswordDTO getUserPassword_Valid() {
        UserPasswordDTO userPasswordDTO = new UserPasswordDTO();
        userPasswordDTO.setUserId(0l);
        userPasswordDTO.setNewPassword("tommy");
        userPasswordDTO.setOldPassword("tommy123");
        userPasswordDTO.setRepeatedPassword("tommy");
        return userPasswordDTO;
    }

    private UserPasswordDTO getUserPassword_Invalid() {
        UserPasswordDTO userPasswordDTO = new UserPasswordDTO();
        userPasswordDTO.setUserId(0l);
        userPasswordDTO.setNewPassword("tommy");
        userPasswordDTO.setOldPassword("tommy123");
        userPasswordDTO.setRepeatedPassword("tommislav");
        return userPasswordDTO;
    }

    private PatientDTO getPatient() {
        PatientDTO patientDTO = new PatientDTO();
        Address address = new Address();
        address.setCountry("Serbia");
        address.setLatitude(213213213.0);
        address.setStreet("Misarska");
        address.setTown("Novi Sad");
        address.setLatitude(213213.0);
        Contact contact = new Contact();
        contact.setAddress(address);
        contact.setPhoneNumber("09999123");
        patientDTO.setContact(contact);
        patientDTO.setEmail("ilija_brdar@yahoo.com");
        patientDTO.setId(0l);
        patientDTO.setFirstName("Ilija");
        patientDTO.setLastName("Brdar");
        return patientDTO;
    }
}