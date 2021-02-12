package app.controller;


import app.dto.MedicationPriceListDTO;
import app.model.time.Period;
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
import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicationPriceListControllerTest {

    private static final String URL_PREFIX = "/api/pricelist";

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
    public void testNewPriceList() throws Exception {
        MedicationPriceListDTO dto = new MedicationPriceListDTO();
        dto.setMedicationId(1L);
        dto.setPharmacyId(1L);
        dto.setCost(999);
        Period period = new Period(LocalDateTime.of(2021,6,14,12,0), LocalDateTime.of(2021,8,27,12,0) );
        dto.setPeriod(period);
        dto.setMedicationName("Aspirin");

        String json = TestUtil.json(dto);

        mockMvc.perform(put(URL_PREFIX + "/newPriceList").contentType(contentType).content(json))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string(""));
    }

}
