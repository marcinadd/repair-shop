package com.marcinadd.repairshop.item;

import com.google.gson.Gson;
import com.marcinadd.repairshop.RepairShopApplication;
import com.marcinadd.repairshop.form.Form;
import com.marcinadd.repairshop.form.FormRepository;
import com.marcinadd.repairshop.item.buyable.Buyable;
import com.marcinadd.repairshop.item.buyable.BuyableRepository;
import com.marcinadd.repairshop.item.buyable.ItemForm;
import com.marcinadd.repairshop.item.buyable.service.Service;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
@AutoConfigureMockMvc
public class ItemControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private BuyableRepository buyableRepository;

    @MockBean
    private FormRepository formRepository;

    private Item item1;
    private Buyable buyable;
    private Form form;

    @Before
    public void init() {
        form = new Form();
        form.setId(32L);
        item1 = new Item();
        item1.setId(34L);
        buyable = new Service();
        buyable.setId(36L);

        Item item2 = new Item();
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        Mockito.when(itemRepository.findAll())
                .thenReturn(items);

        Mockito.when(buyableRepository.findById(buyable.getId()))
                .thenReturn(java.util.Optional.ofNullable(buyable));
        Mockito.when(formRepository.findById(form.getId()))
                .thenReturn(java.util.Optional.ofNullable(form));
        Mockito.when(itemRepository.save(any(Item.class)))
                .thenReturn(item1);
        Mockito.when(itemRepository.findById(item1.getId()))
                .thenReturn(java.util.Optional.ofNullable(item1));
    }

    @Test
    public void whenGetItem_shouldReturnItems() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void whenCreateItem_shouldReturnItem() throws Exception {
        ItemForm itemForm = new ItemForm();
        itemForm.setQuantity(2);
        itemForm.setFormId(form.getId());
        itemForm.setBuyableId(buyable.getId());
        mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(itemForm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item1.getId()));
    }

    @Test
    public void whenDeleteItem_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/items/" + item1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }
}
