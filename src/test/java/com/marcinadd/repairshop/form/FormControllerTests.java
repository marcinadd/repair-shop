package com.marcinadd.repairshop.form;

import com.google.gson.Gson;
import com.marcinadd.repairshop.RepairShopApplication;
import com.marcinadd.repairshop.client.Client;
import com.marcinadd.repairshop.client.ClientRepository;
import com.marcinadd.repairshop.repairable.Repairable;
import com.marcinadd.repairshop.repairable.RepairableRepository;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
@AutoConfigureMockMvc
public class FormControllerTests {
    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private RepairableRepository repairableRepository;

    @MockBean
    private FormRepository formRepository;

    @Autowired
    private MockMvc mockMvc;

    private Client client;
    private Repairable repairable;

    @Before
    public void init() {
        client = Client.builder()
                .id(1L)
                .firstName("Sample")
                .build();
        repairable = Repairable.builder()
                .id(2L)
                .owner(client)
                .build();
        Form form = Form.builder()
                .id(3L)
                .repairable(repairable)
                .client(client)
                .build();
        Mockito.when(clientRepository.findById(client.getId()))
                .thenReturn(java.util.Optional.of(client));
        Mockito.when(repairableRepository.findById(repairable.getId()))
                .thenReturn(java.util.Optional.of(repairable));
        Mockito.when(formRepository.save(any(Form.class)))
                .thenReturn(form);

    }

    @Test
    public void whenCreateForm_shouldReturnForm() throws Exception {
        Form form = Form.builder().repairableId(repairable.getId()).clientId(client.getId()).build();

        mockMvc.perform(post("/forms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)));
    }
}
