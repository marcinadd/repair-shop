package com.marcinadd.repairshop.item.buyable.part;

import com.google.gson.Gson;
import com.marcinadd.repairshop.RepairShopApplication;
import com.marcinadd.repairshop.item.buyable.BuyableRepository;
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

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class PartControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartRepository partRepository;

    @MockBean
    private BuyableRepository buyableRepository;

    private Part part;

    @Before
    public void init() {
        part = new Part();
        part.setId(12L);
        Mockito.when(partRepository.save(any(Part.class)))
                .thenReturn(part);

        List<Part> parts = new ArrayList<>();
        parts.add(new Part());
        parts.add(new Part());
        Mockito.when(partRepository.findByDeletedIsFalse())
                .thenReturn(parts);

        Mockito.when(partRepository.findById(part.getId()))
                .thenReturn(java.util.Optional.ofNullable(part));

        Mockito.when(buyableRepository.findById(part.getId()))
                .thenReturn(java.util.Optional.ofNullable(part));
    }

    @Test
    @WithMockUser
    public void whenCreatePart_shouldReturnPart() throws Exception {
        Part createdPart = new Part();
        mockMvc.perform(post("/parts").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(createdPart)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(part.getId()));
    }

    @Test
    @WithMockUser
    public void whenGetParts_shouldReturnPartsList() throws Exception {
        mockMvc.perform(get("/parts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockUser
    public void whenUpdatePart_shouldReturnPart() throws Exception {
        Part updatedPart = new Part();
        part.setPrice(new BigDecimal("3.35"));
        mockMvc.perform(patch("/parts/" + part.getId()).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(updatedPart)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(part.getId()));
    }

    @Test
    @WithMockUser
    public void whenGetDeletePart_shouldReturnTrue() throws Exception {
        mockMvc.perform(delete("/parts/" + part.getId()).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

}
