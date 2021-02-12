package app.controller;

import app.dto.MedicationOfferDTO;
import app.dto.MedicationSupplierDTO;
import app.model.medication.MedicationOfferStatus;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateOfferControllerTest {
    private static final String URL_PREFIX = "/api/medicationOffer";

    private MediaType contentType = new MediaType(org.springframework.http.MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void set() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(roles = "supplier")
    public void createMedicationOfferTest() throws Exception {
        MedicationOfferDTO dto=new MedicationOfferDTO();
        dto.setCost(111);
        dto.setShippingDate(LocalDateTime.of(2022,5,23, 12,0,0,0));
        dto.setStatus(MedicationOfferStatus.pending);
        dto.setMedicationOrderId(1L);
        dto.setSupplierId(55L);
        dto.setSupplierFirstName("Jovana");
        dto.setSupplierLastName("Markovic");

        String json = TestUtil.json(dto);

        mockMvc.perform(post(URL_PREFIX + "/new").contentType(contentType).content(json))
                .andExpect(status().isBadRequest());
    }


}
