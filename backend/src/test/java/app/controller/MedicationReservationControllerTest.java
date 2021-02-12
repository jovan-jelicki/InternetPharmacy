package app.controller;

import app.dto.GetMedicationReservationDTO;
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

import java.lang.reflect.Proxy;
import java.net.URL;
import java.nio.charset.Charset;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicationReservationControllerTest {
    private static final String URL_PREFIX = "/api/medicationReservation";

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
    @WithMockUser(roles = "pharmacist")
    public void testGetMedicationReservationFromPharmacy() throws Exception {
        GetMedicationReservationDTO getMedicationReservationDTO = new GetMedicationReservationDTO();
        getMedicationReservationDTO.setMedicationId(0l);
        getMedicationReservationDTO.setPharmacistId(1l);

        String json = TestUtil.json(getMedicationReservationDTO);

        mockMvc.perform(post(URL_PREFIX + "/getMedicationReservation").contentType(contentType).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Tom"));
    }

}
