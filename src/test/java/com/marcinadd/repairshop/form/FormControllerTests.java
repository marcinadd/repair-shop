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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

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
public class FormControllerTests {
    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private RepairableRepository repairableRepository;

    @MockBean
    private FormRepository formRepository;

    private final String rawPassword = "rawPass";

    @Autowired
    private MockMvc mockMvc;

    private Client client;
    private Repairable repairable;
    @MockBean
    private PasswordEncoder passwordEncoder;
    private Form form;

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
        String encodedPassword = "encodedPassword";
        form = Form.builder()
                .id(3L)
                .description("Sample desc")
                .repairable(repairable)
                .client(client)
                .password(encodedPassword)
                .build();
        Mockito.when(clientRepository.findById(client.getId()))
                .thenReturn(java.util.Optional.of(client));
        Mockito.when(repairableRepository.findById(repairable.getId()))
                .thenReturn(java.util.Optional.of(repairable));
        Mockito.when(formRepository.save(any(Form.class)))
                .thenReturn(form);

        Mockito.when(formRepository.findAll())
                .thenReturn(Collections.singletonList(form));

        Mockito.when(formRepository.findById(form.getId()))
                .thenReturn(java.util.Optional.of(form));

        Mockito.when(passwordEncoder.matches(rawPassword, encodedPassword))
                .thenReturn(true);
    }

    @Test
    @WithMockUser
    public void whenCreateForm_shouldReturnForm() throws Exception {
        Form form = Form.builder().repairableId(repairable.getId()).clientId(client.getId()).build();

        mockMvc.perform(post("/forms").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)));
    }

    @Test
    @WithMockUser
    public void whenGetForms_shouldReturnFormList() throws Exception {
        mockMvc.perform(get("/forms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser
    public void whenGetFormById_shouldReturnForm() throws Exception {
        mockMvc.perform(get("/forms/" + form.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.description", is(form.getDescription())));
    }

    @Test
    @WithMockUser
    public void whenPatchStatus_shouldReturnOk() throws Exception {
        Form patched = Form.builder().status(Status.COMPLETED).build();
        mockMvc.perform(patch("/forms/" + form.getId()).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(patched)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.description", is(form.getDescription())));
    }

    @Test
    @WithMockUser
    public void whenRegeneratePassword_shouldReturnFormList() throws Exception {
        mockMvc.perform(get("/forms/" + form.getId() + "/regenerateClientPasswordToPdf"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void whenGetFormInfo_shouldReturnFormInfo() throws Exception {
        mockMvc.perform(post("/forms/" + form.getId() + "/info").with(csrf())
                .content(rawPassword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)));
    }

    @Test
    @WithMockUser
    public void whenGetFormInfoWithIncorrectPassword_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(post("/forms/" + form.getId() + "/info").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("incorrectPassword"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void whenGetFormInfoWhichNotExists_shouldReturnNotFound() throws Exception {
        mockMvc.perform(post("/forms/" + form.getId() + 1 + "/info").with(csrf())
                .content(rawPassword))
                .andExpect(status().isNotFound());
    }
}
