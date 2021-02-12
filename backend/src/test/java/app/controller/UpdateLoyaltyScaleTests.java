package app.controller;

import app.model.pharmacy.LoyaltyCategory;
import app.model.pharmacy.LoyaltyProgram;
import app.model.pharmacy.LoyaltyScale;
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
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest

public class UpdateLoyaltyScaleTests {
    private static final String URL_PREFIX = "/api/loyaltyScale";

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
    public void testEditLoyaltyScale() throws Exception {
        LoyaltyScale loyaltyScale= new LoyaltyScale();
        loyaltyScale.setId(1000L);
        loyaltyScale.setCategory(LoyaltyCategory.gold);
        loyaltyScale.setMinPoints(80);
        loyaltyScale.setMaxPoints(90);
        loyaltyScale.setDiscount(50);

        String json = TestUtil.json(loyaltyScale);

        mockMvc.perform(post(URL_PREFIX + "/save").contentType(contentType).content(json))
                .andExpect(status().isCreated());

    }

}
