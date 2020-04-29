package com.marcinadd.repairshop.item;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
@AutoConfigureMockMvc
public class ItemServiceTests {
    @Autowired
    private FormRepository formRepository;
    @Autowired
    private BuyableRepository buyableRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemService itemService;

    private Form form;
    private Buyable buyable;

    @Before
    public void init() {
        form = new Form();
        form = formRepository.save(form);
        buyable = new Service();
        buyable.setPrice(new BigDecimal(3.0));
        buyable = buyableRepository.save(buyable);
    }

    @Test
    @Transactional
    public void whenCreateItem_shouldAddItToForm() {
        ItemForm itemForm = new ItemForm();
        itemForm.setBuyableId(buyable.getId());
        itemForm.setFormId(form.getId());
        itemForm.setQuantity(3);
        Item item = itemService.createItem(itemForm);
        assertThat(item.getForm(), is(form));
    }

    @Test
    public void whenDeleteItemWhichExists_shouldReturnTrue() {
        Item item = new Item();
        item = itemRepository.save(item);
        assertThat(itemService.deleteItemById(item.getId()), is(true));
    }

    @Test
    public void whenDeleteItemWhichNotExists_shouldReturnFalse() {
        assertThat(itemService.deleteItemById(123456789L), is(false));
    }
}
