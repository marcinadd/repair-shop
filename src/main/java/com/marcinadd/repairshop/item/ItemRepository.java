package com.marcinadd.repairshop.item;

import com.marcinadd.repairshop.item.buyable.Buyable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Long countByBuyable(Buyable buyable);
}
