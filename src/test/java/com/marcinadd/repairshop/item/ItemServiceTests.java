package com.marcinadd.repairshop.item;

import com.marcinadd.repairshop.RepairShopApplication;
import com.marcinadd.repairshop.form.Form;
import com.marcinadd.repairshop.form.FormRepository;
import com.marcinadd.repairshop.item.buyable.BuyableRepository;
import com.marcinadd.repairshop.item.buyable.ItemForm;
import com.marcinadd.repairshop.item.buyable.part.NotEnoughPartsInStockException;
import com.marcinadd.repairshop.item.buyable.part.Part;
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
    private Service service;
    private Part part;

    @Before
    public void init() {
        form = new Form();
        form = formRepository.save(form);
        service = new Service();
        service.setPrice(new BigDecimal(3.0));
        service = buyableRepository.save(service);
        part = new Part();
        part.setPrice(new BigDecimal(4.5));
        part.setInStockQuantity(4);
        part = buyableRepository.save(part);
    }

    @Test
    @Transactional
    public void whenCreateItemWithService_shouldAddItToForm() throws NotEnoughPartsInStockException {
        ItemForm itemForm = new ItemForm();
        itemForm.setBuyableId(service.getId());
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

    @Test
    public void whenCreateItemWithPartWithQuantityInStock_shouldCreateItemAndDecrementQuantity() throws NotEnoughPartsInStockException {
        Integer currentPartQuantity = part.getInStockQuantity();
        Integer formQuantity = 3;
        ItemForm itemForm = new ItemForm();
        itemForm.setBuyableId(part.getId());
        itemForm.setFormId(form.getId());
        itemForm.setQuantity(formQuantity);
        Item item = itemService.createItem(itemForm);
        assertThat(((Part) item.getBuyable()).getInStockQuantity(), is(currentPartQuantity - formQuantity));
    }

    @Test(expected = NotEnoughPartsInStockException.class)
    public void whenCreateItemWithPartWithQuantityOutOfStock_shouldThrowException() throws NotEnoughPartsInStockException {
        Integer formQuantity = 99999999;
        ItemForm itemForm = new ItemForm();
        itemForm.setBuyableId(part.getId());
        itemForm.setFormId(form.getId());
        itemForm.setQuantity(formQuantity);
        itemService.createItem(itemForm);
    }
}
