package com.marcinadd.repairshop.item.buyable.service;

import com.google.gson.Gson;
import com.marcinadd.repairshop.RepairShopApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
@AutoConfigureMockMvc
public class ServiceControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceRepository serviceRepository;

    private Service service;

    @Before
    public void init() {
        service = new Service();
        service.setId(12L);
        Mockito.when(serviceRepository.save(any(Service.class)))
                .thenReturn(service);
    }

    @Test
    @WithMockUser
    public void whenCreateService_shouldReturnService() throws Exception {
        Service createdService = new Service();
        mockMvc.perform(post("/services").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(createdService)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(service.getId()));
    }
}
