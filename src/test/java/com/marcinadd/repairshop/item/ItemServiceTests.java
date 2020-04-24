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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

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
    private ItemService itemService;

    private Form form;
    private Buyable buyable;

    @Before
    public void init() {
        form = new Form();
        form = formRepository.save(form);
        buyable = new Service();
        buyable = buyableRepository.save(buyable);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    @Transactional
    public void whenCreateItem_shouldAddItToForm() {
        ItemForm itemForm = new ItemForm();
        itemForm.setBuyableId(buyable.getId());
        itemForm.setFormId(form.getId());
        itemForm.setQuantity(3);

        Item item = itemService.createItem(itemForm);
        form = formRepository.findById(this.form.getId()).get();
        assertThat(form.getItems(), contains(item));

    }

}
