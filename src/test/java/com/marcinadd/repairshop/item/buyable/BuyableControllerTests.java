package com.marcinadd.repairshop.item.buyable;

import com.marcinadd.repairshop.RepairShopApplication;
import com.marcinadd.repairshop.item.buyable.service.Service;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
@AutoConfigureMockMvc
public class BuyableControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuyableRepository buyableRepository;

    @Before
    public void init() {
        List<Buyable> buyables = new ArrayList<>();
        Buyable buyable1 = new Service();
        buyable1.setId(1L);
        Buyable buyable2 = new Service();
        buyable2.setId(2L);
        Buyable buyable3 = new Service();
        buyable3.setId(3L);
        buyables.add(buyable1);
        buyables.add(buyable2);
        buyables.add(buyable3);
        Mockito.when(buyableRepository.findAll())
                .thenReturn(buyables);
    }

    @Test
    public void whenGetBuyables_shouldReturnBuyables() throws Exception {
        mockMvc.perform(get("/buyables"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

}
