package com.marcinadd.repairshop.item;

import com.marcinadd.repairshop.item.buyable.ItemForm;
import com.marcinadd.repairshop.item.buyable.part.NotEnoughPartsInStockException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public Item createItem(@RequestBody @Valid ItemForm itemForm) {
        try {
            return itemService.createItem(itemForm);
        } catch (NotEnoughPartsInStockException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough parts", e);
        }
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
