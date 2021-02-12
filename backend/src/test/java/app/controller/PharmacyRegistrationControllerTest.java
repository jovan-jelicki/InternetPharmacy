package app.controller;


import app.dto.PharmacyAdminPharmacyDTO;
import app.model.user.Address;
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
public class PharmacyRegistrationControllerTest {
    private static final String URL_PREFIX = "/api/pharmacy";

    private MediaType contentType = new MediaType(org.springframework.http.MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void set() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }
    private String name;
    private String description;
    private Address address;
    private Long pharmacyAdminId;


    @Test
    @WithMockUser(roles = "systemAdmin")
    public void pharmacyRegistrationTest() throws Exception {
        Address address=new Address();
        address.setLongitude(40.0);
        address.setLatitude(21.1);
        address.setTown("Beograd");
        address.setCountry("Srbija");
        address.setStreet("Kisacka 5");

        PharmacyAdminPharmacyDTO  pharmacyAdminPharmacyDTO=new PharmacyAdminPharmacyDTO();
        pharmacyAdminPharmacyDTO.setName("Milena");
        pharmacyAdminPharmacyDTO.setDescription("Najbolje za najbolje");
        pharmacyAdminPharmacyDTO.setPharmacyAdminId(1L);
        pharmacyAdminPharmacyDTO.setAddress(address);

        String json = TestUtil.json(pharmacyAdminPharmacyDTO);


        mockMvc.perform(post(URL_PREFIX + "/save").contentType(contentType).content(json))
                .andExpect(status().isOk());
    }



}
