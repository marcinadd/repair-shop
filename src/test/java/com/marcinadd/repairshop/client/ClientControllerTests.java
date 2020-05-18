package com.marcinadd.repairshop.client;

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

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
@AutoConfigureMockMvc
public class ClientControllerTests {
    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private MockMvc mockMvc;

    private Client client;
    @Before
    public void init() {
        client = Client.builder()
                .id(1L)
                .firstName("Adam")
                .lastName("Test")
                .phone("+48123456789")
                .email("example@example.com")
                .build();

        List<Client> clients = Collections.singletonList(client);
        Mockito.when(clientRepository.findAll())
                .thenReturn(clients);

        Mockito.when(clientRepository.findById(client.getId()))
                .thenReturn(java.util.Optional.of(client));
        Mockito.when(clientRepository.save(any(Client.class))).thenReturn(client);
        Mockito.when(clientRepository.findByLastNameStartingWithIgnoreCase("T"))
                .thenReturn(Collections.singletonList(client));
    }

    @Test
    @WithMockUser
    public void testGetAllClients() throws Exception {
        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser
    public void testCreateClient() throws Exception {
        Client test = Client.builder()
                .firstName("Sample")
                .lastName("Sample2")
                .build();

        mockMvc.perform(post("/clients").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(test)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @WithMockUser
    public void testLastNameStartsWith() throws Exception {
        mockMvc.perform(get("/clients?lastNameStartsWith=T"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser
    public void testFindById() throws Exception {
        mockMvc.perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @WithMockUser
    public void testUpdateClientData() throws Exception {
        Client toUpdateInfo = Client.builder()
                .id(1L)
                .firstName("NewName")
                .lastName("NewName")
                .phone("+48123456789")
                .email("newmail@example.com")
                .repairables(null)
                .build();
        mockMvc.perform(patch("/clients/1").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(toUpdateInfo)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    public void whenDeleteItem_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/clients/" + client.getId()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }
}
