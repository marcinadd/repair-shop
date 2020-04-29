package com.marcinadd.repairshop.item.buyable;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/buyables")
@CrossOrigin("*")
public class BuyableController {
    private final BuyableService buyableService;

    public BuyableController(BuyableService buyableService) {
        this.buyableService = buyableService;
    }

    @GetMapping
    public List<Buyable> getBuyables() {
        return buyableService.findAll();
    }
}
