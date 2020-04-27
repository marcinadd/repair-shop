package com.marcinadd.repairshop.pdf;

import com.marcinadd.repairshop.RepairShopApplication;
import com.marcinadd.repairshop.form.Form;
import com.marcinadd.repairshop.form.FormRepository;
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
        form = new Form();
        form.setId(1L);

        Mockito.when(formRepository.findById(form.getId()))
                .thenReturn(java.util.Optional.of(form));
    }

    @Test
    public void whenGetFormSummaryPdf_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/pdf/forms/" + form.getId()))
                .andExpect(status().isOk());
    }

}
