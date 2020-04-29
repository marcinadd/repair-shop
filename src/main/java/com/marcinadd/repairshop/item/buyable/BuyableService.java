package com.marcinadd.repairshop.item.buyable;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyableService {
    private final BuyableRepository buyableRepository;

    public BuyableService(BuyableRepository buyableRepository) {
        this.buyableRepository = buyableRepository;
    }


    public List<Buyable> findAll() {
        return buyableRepository.findAll();
    }
}
