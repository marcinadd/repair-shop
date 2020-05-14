package com.marcinadd.repairshop.item;

import com.marcinadd.repairshop.item.buyable.ItemForm;
import com.marcinadd.repairshop.item.buyable.part.NotEnoughPartsInStockException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public Item createItem(@RequestBody @Valid ItemForm itemForm) throws NotEnoughPartsInStockException {
        return itemService.createItem(itemForm);
    }

    @GetMapping
    public List<Item> getItems() {
        return itemService.findAllItems();
    }

    @DeleteMapping("{id}")
    public boolean delete(@PathVariable("id") Long id) {
        return itemService.deleteItemById(id);
    }
}
