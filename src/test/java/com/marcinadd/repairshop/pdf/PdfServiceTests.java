package com.marcinadd.repairshop.pdf;

import com.marcinadd.repairshop.RepairShopApplication;
import com.marcinadd.repairshop.item.Item;
import com.marcinadd.repairshop.pdf.generator.ItemListGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
public class PdfServiceTests {

    @Autowired
    private ItemListGenerator itemListGenerator;

    @Test
    public void whenCreateForm_shouldReturnForm() {
        List<Item> items = new ArrayList<>();
        items.add(Item.builder().itemPrice(new BigDecimal(2.5)).quantity(2).build());
        items.add(Item.builder().itemPrice(new BigDecimal(1.25)).quantity(1).build());
        BigDecimal totalPrice = itemListGenerator.countTotalPrice(items);
        assertThat(totalPrice, is(new BigDecimal(6.25)));
    }

}
