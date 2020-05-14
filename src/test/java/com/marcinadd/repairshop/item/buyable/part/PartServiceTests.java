package com.marcinadd.repairshop.item.buyable.part;

import com.marcinadd.repairshop.RepairShopApplication;
import com.marcinadd.repairshop.item.Item;
import com.marcinadd.repairshop.item.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
@AutoConfigureMockMvc
public class PartServiceTests {
    @Autowired
    private PartService partService;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private ItemRepository itemRepository;

    private Part part;

    @Before
    public void init() {
        part = new Part();
        part.setName("Sample");
        part.setPrice(new BigDecimal("3.02"));
        part.setInStockQuantity(3);
        part = partRepository.save(new Part());
    }

    @Test
    public void whenUpdatePartPrice_shouldReturnUpdatedPart() {
        Part updatedValues = new Part();
        updatedValues.setInStockQuantity(4);
        updatedValues.setPrice(new BigDecimal("4.45"));
        updatedValues.setName("New name");
        part = partService.updatePart(part.getId(), updatedValues);
        assertThat(part.getInStockQuantity(), is(updatedValues.getInStockQuantity()));
        assertThat(part.getPrice(), is(updatedValues.getPrice()));
        assertThat(part.getName(), not(updatedValues.getName()));
    }

    @Test
    public void whenDeleteUnreferencedPart_shouldDeleteIt() {
        Part part = new Part();
        part = partRepository.save(part);
        Long id = part.getId();
        assertTrue(partService.deletePart(part.getId()));
        Assert.assertFalse(partRepository.findById(id).isPresent());
    }

    @Test
    public void whenDeleteReferencedPart_shouldSetDeletedToTrue() {
        Part part = new Part();
        part = partRepository.save(part);
        Long id = part.getId();

        Item item = new Item();
        item.setBuyable(part);
        itemRepository.save(item);

        assertTrue(partService.deletePart(part.getId()));
        Optional<Part> optionalPart = partRepository.findById(id);
        assertTrue(optionalPart.isPresent());
        assertTrue(optionalPart.get().getDeleted());
    }

    @Test
    public void whenTryToDeleteNotExistingPart_shouldReturnFalse() {
        Assert.assertFalse(partService.deletePart(Long.MAX_VALUE));
    }
}
