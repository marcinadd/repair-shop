package com.marcinadd.repairshop.repairable;

import com.google.gson.Gson;
import com.marcinadd.repairshop.RepairShopApplication;
import com.marcinadd.repairshop.client.Client;
import com.marcinadd.repairshop.client.ClientRepository;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
@AutoConfigureMockMvc
public class RepairableControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private RepairableRepository repairableRepository;

    private Client owner;

    @Before
    public void init() {
        owner = new Client();
        owner.setId(123L);
        Mockito.when(clientRepository.findById(owner.getId()))
                .thenReturn(java.util.Optional.ofNullable(owner));
        Repairable repairable = Repairable.builder()
                .id(12L)
                .owner(owner)
                .build();
        Repairable repairable1 = Repairable.builder()
                .id(13L)
                .owner(owner)
                .build();
        List<Repairable> repairables = new ArrayList<>(Arrays.asList(repairable, repairable1));

        Mockito.when(repairableRepository.save(any(Repairable.class)))
                .thenReturn(repairable);

        Mockito.when(repairableRepository.findByOwner(owner))
                .thenReturn(repairables);
    }

    @Test
    @WithMockUser
    public void whenCreateRepairable_shouldReturnRepairable() throws Exception {
        Repairable repairable = Repairable.builder()
                .id(12L)
                .ownerId(owner.getId()).build();
        mockMvc.perform(post("/repairables").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(repairable)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(12L));
    }

    @Test
    @WithMockUser
    public void whenGetRepairablesByOwnerId_shouldReturnRepairables() throws Exception {
        Repairable repairable = Repairable.builder()
                .ownerId(123L).build();
        mockMvc.perform(get("/repairables?ownerId=" + owner.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(repairable)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
