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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
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
        Mockito.when(clientRepository.findById(123L))
                .thenReturn(java.util.Optional.ofNullable(owner));

        Repairable repairable = Repairable.builder()
                .id(12L)
                .owner(owner)
                .build();

        Mockito.when(repairableRepository.save(any(Repairable.class)))
                .thenReturn(repairable);
    }


    @Test
    public void whenCreateRepairable_shouldReturnRepairable() throws Exception {
        Repairable repairable = Repairable.builder()
                .ownerId(123L).build();
        mockMvc.perform(post("repairables/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(repairable)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));

    }


}
