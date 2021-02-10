package app.controller;

import app.dto.ExaminerDTO;
import app.dto.SearchFinishedAppointments;
import app.model.user.EmployeeType;
import app.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.management.relation.Role;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;


import java.nio.charset.Charset;


@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentControllerTest {

    private static final String URL_PREFIX = "/api/appointment";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void set() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @WithMockUser(roles = "pharmacist")
    @Test
    public void testSearchFinishedAppointments() throws Exception {
        SearchFinishedAppointments searchFinishedAppointments = new SearchFinishedAppointments();
        searchFinishedAppointments.setId(3l);
        searchFinishedAppointments.setType(EmployeeType.ROLE_dermatologist);
        searchFinishedAppointments.setQuery("Tom");

        String json = TestUtil.json(searchFinishedAppointments);

        mockMvc.perform(post(URL_PREFIX + "/searchFinishedAppointments").contentType(contentType).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].patientLastName").value(hasItem("Peterson")));
    }

    @Test
    @WithMockUser(roles = "pharmacist")
    public void testGetFinishedByExaminer() throws Exception {
        ExaminerDTO examinerDTO = new ExaminerDTO();
        examinerDTO.setType(EmployeeType.ROLE_dermatologist);
        examinerDTO.setId(3l);

        String json = TestUtil.json(examinerDTO);

        mockMvc.perform(post(URL_PREFIX + "/getFinishedByExaminer").contentType(contentType).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());


    }
}
