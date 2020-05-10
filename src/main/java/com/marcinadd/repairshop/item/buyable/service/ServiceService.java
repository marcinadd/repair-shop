package com.marcinadd.repairshop.item.buyable.service;

import com.marcinadd.repairshop.item.buyable.BuyableService;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final BuyableService buyableService;

    public ServiceService(ServiceRepository serviceRepository, BuyableService buyableService) {
        this.serviceRepository = serviceRepository;
        this.buyableService = buyableService;
    }

    public Service createService(Service service) {
        return serviceRepository.save(service);
    }

    public List<Service> getServices() {
        return serviceRepository.findAll();
    }

    public Service updateService(Long serviceId, Service toUpdateService) {
        Optional<Service> optionalService = serviceRepository.findById(serviceId);
        if (optionalService.isPresent()) {
            Service service = optionalService.get();
            if (toUpdateService.getPrice() != null) service.setPrice(toUpdateService.getPrice());
            return serviceRepository.save(service);
        }
        return null;
    }

    public boolean deleteService(Long serviceId) {
        return buyableService.deleteBuyable(serviceId);
    }
}
