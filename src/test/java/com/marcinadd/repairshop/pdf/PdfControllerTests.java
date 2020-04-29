package com.marcinadd.repairshop.pdf;

import com.marcinadd.repairshop.RepairShopApplication;
import com.marcinadd.repairshop.client.Client;
import com.marcinadd.repairshop.form.Form;
import com.marcinadd.repairshop.form.FormRepository;
import com.marcinadd.repairshop.item.Item;
import com.marcinadd.repairshop.item.buyable.service.Service;
import com.marcinadd.repairshop.repairable.Repairable;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
@AutoConfigureMockMvc
public class PdfControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FormRepository formRepository;

    private Form form;

    @Before
    public void init() {
        Client client = new Client();
        Repairable repairable = new Repairable();
        List<Item> items = new ArrayList<>();
        items.add(Item.builder()
                .buyable(new Service())
                .itemPrice(new BigDecimal(3.25))
                .quantity(2)
                .build());
        form = Form.builder()
                .id(1L)
                .client(client)
                .repairable(repairable)
                .items(items)
                .build();

        Mockito.when(formRepository.findById(form.getId()))
                .thenReturn(java.util.Optional.of(form));
    }

    @Test
    public void whenGetFormSummaryPdf_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/pdf/forms/" + form.getId()))
                .andExpect(status().isOk());
    }

}
