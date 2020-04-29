package com.marcinadd.repairshop.item;

import com.marcinadd.repairshop.form.Form;
import com.marcinadd.repairshop.form.FormRepository;
import com.marcinadd.repairshop.item.buyable.Buyable;
import com.marcinadd.repairshop.item.buyable.BuyableRepository;
import com.marcinadd.repairshop.item.buyable.ItemForm;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final BuyableRepository buyableRepository;
    private final FormRepository formRepository;
    private final ItemRepository itemRepository;

    public ItemService(BuyableRepository buyableRepository, FormRepository formRepository, ItemRepository itemRepository) {
        this.buyableRepository = buyableRepository;
        this.formRepository = formRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public Item createItem(ItemForm itemForm) {
        Optional<Buyable> optionalBuyable = buyableRepository.findById(itemForm.getBuyableId());
        Optional<Form> optionalForm = formRepository.findById(itemForm.getFormId());
        if (optionalForm.isPresent() && optionalBuyable.isPresent()) {
            Buyable buyable = optionalBuyable.get();
            Form form = optionalForm.get();
            Item item = Item.builder()
                    .buyable(buyable)
                    .itemPrice(buyable.getPrice())
                    .quantity(itemForm.getQuantity())
                    .form(form)
                    .build();
            return itemRepository.save(item);
        }
        return null;
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public boolean deleteItemById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            itemRepository.delete(optionalItem.get());
            return true;
        }
        return false;
    }
}
