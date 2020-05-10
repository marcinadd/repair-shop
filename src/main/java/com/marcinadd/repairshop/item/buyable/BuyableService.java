package com.marcinadd.repairshop.item.buyable;

import com.marcinadd.repairshop.item.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuyableService {
    private final BuyableRepository buyableRepository;
    private final ItemRepository itemRepository;

    public BuyableService(BuyableRepository buyableRepository, ItemRepository itemRepository) {
        this.buyableRepository = buyableRepository;
        this.itemRepository = itemRepository;
    }
    public List<Buyable> findAll() {
        return buyableRepository.findByDeletedIsFalse();
    }

    public boolean deleteBuyable(Long buyableId) {
        Optional<Buyable> optionalBuyable = buyableRepository.findById(buyableId);
        if (optionalBuyable.isPresent()) {
            Buyable buyable = optionalBuyable.get();
            Long items = itemRepository.countByBuyable(buyable);
            if (items == 0) {
                buyableRepository.delete(buyable);
            } else {
                buyable.setDeleted(true);
                buyableRepository.save(buyable);
            }
            return true;
        }
        return false;
    }
}
