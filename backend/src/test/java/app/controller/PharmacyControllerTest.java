package app.controller;


import app.dto.PharmacyMedicationListingDTO;
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
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
public class PharmacyControllerTest {

    private static final String URL_PREFIX = "/api/pharmacy";

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
    @WithMockUser(roles = "pharmacyAdmin")
    public void testDeleteMedicationFromPharmacy() throws Exception {
        PharmacyMedicationListingDTO dto = new PharmacyMedicationListingDTO();
        dto.setMedicationId(0L);
        dto.setPharmacyId(1L);
        dto.setMedicationQuantityId(1L);

        String json = TestUtil.json(dto);

        mockMvc.perform(put(URL_PREFIX + "/deleteMedicationFromPharmacy").contentType(contentType).content(json))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(roles = "pharmacyAdmin")
    public void testGetPharmacyMedicationListing() throws Exception {
        long pharmacyId = 1L;
        mockMvc.perform(get(URL_PREFIX + "/getPharmacyMedicationListing/" + pharmacyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].name").value(hasItem("Xanax")));
    }

}
