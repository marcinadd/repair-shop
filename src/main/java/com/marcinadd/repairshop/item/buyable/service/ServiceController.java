package com.marcinadd.repairshop.item.buyable.service;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping
    public Service create(@RequestBody Service service) {
        return serviceService.createService(service);
    }
}
