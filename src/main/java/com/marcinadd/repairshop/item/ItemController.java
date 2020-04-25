package com.marcinadd.repairshop.item;

import com.marcinadd.repairshop.item.buyable.ItemForm;
import org.springframework.web.bind.annotation.*;

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
    public Item createItem(@RequestBody ItemForm itemForm) {
        return itemService.createItem(itemForm);
    }

    @GetMapping
    public List<Item> getItems() {
        return itemService.findAllItems();
    }
}
