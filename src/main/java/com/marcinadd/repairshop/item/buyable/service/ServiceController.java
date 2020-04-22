package com.marcinadd.repairshop.item.buyable.service;

import com.marcinadd.repairshop.item.Item;
import com.marcinadd.repairshop.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("services")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public void test() {
        Service service = new Service();
        service.setName("Wymiana matrycy");
        service.setPrice(new BigDecimal(75.25));
        service = serviceRepository.save(service);

        Item item = new Item();
        item.setBuyable(service);
        item.setItemPrice(service.getPrice());
        item.setQuantity(2);
        itemRepository.save(item);
    }

    @GetMapping("list")
    public List<Item> list() {
        return itemRepository.findAll();
    }

}
