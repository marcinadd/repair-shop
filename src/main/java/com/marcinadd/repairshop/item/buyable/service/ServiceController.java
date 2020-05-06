package com.marcinadd.repairshop.item.buyable.service;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<Service> services() {
        return serviceService.getServices();
    }

    @PatchMapping("{id}")
    public Service patchService(@PathVariable("id") Long id, @RequestBody Service service) {
        return serviceService.updateService(id, service);
    }

    @DeleteMapping("{id}")
    public boolean deleteService(@PathVariable("id") Long id) {
        return serviceService.deleteService(id);
    }
}
