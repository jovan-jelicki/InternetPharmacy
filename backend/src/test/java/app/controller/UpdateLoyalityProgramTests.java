package app.controller;

import app.model.pharmacy.LoyaltyProgram;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateLoyalityProgramTests {
    private static final String URL_PREFIX = "/api/loyaltyProgram";

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
    @WithMockUser(roles = "systemAdmin")
    public void testEditLoyaltyProgram() throws Exception {
        LoyaltyProgram loyaltyProgram=new LoyaltyProgram();
        loyaltyProgram.setId(0L);
        loyaltyProgram.setAppointmentPoints(11);
        loyaltyProgram.setConsultingPoints(33);

        String json = TestUtil.json(loyaltyProgram);

        mockMvc.perform(post(URL_PREFIX + "/save").contentType(contentType).content(json))
                .andExpect(status().isCreated());

    }


}
