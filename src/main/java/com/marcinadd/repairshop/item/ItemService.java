package com.marcinadd.repairshop.item;

import com.marcinadd.repairshop.form.Form;
import com.marcinadd.repairshop.form.FormRepository;
import com.marcinadd.repairshop.form.FormService;
import com.marcinadd.repairshop.item.buyable.Buyable;
import com.marcinadd.repairshop.item.buyable.BuyableRepository;
import com.marcinadd.repairshop.item.buyable.ItemForm;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ItemService {
    private final BuyableRepository buyableRepository;
    private final FormRepository formRepository;
    private final ItemRepository itemRepository;
    private final FormService formService;

    public ItemService(BuyableRepository buyableRepository, FormRepository formRepository, ItemRepository itemRepository, FormService formService) {
        this.buyableRepository = buyableRepository;
        this.formRepository = formRepository;
        this.itemRepository = itemRepository;
        this.formService = formService;
    }

    @Transactional
    public Item createItem(ItemForm itemForm) {
        Optional<Buyable> optionalBuyable = buyableRepository.findById(itemForm.getBuyableId());
        Optional<Form> optionalForm = formRepository.findById(itemForm.getFormId());
        if (optionalForm.isPresent() && optionalBuyable.isPresent()) {
            Buyable buyable = optionalBuyable.get();
            Item item = Item.builder()
                    .buyable(buyable)
                    .itemPrice(buyable.getPrice())
                    .quantity(itemForm.getQuantity())
                    .build();
            item = itemRepository.save(item);
            formService.addItem(optionalForm.get(), item);
            return item;
        }
        return null;
    }
}
