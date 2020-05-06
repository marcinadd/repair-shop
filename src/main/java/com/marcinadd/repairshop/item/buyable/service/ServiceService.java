package com.marcinadd.repairshop.item.buyable.service;

import com.marcinadd.repairshop.item.ItemRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final ItemRepository itemRepository;

    public ServiceService(ServiceRepository serviceRepository, ItemRepository itemRepository) {
        this.serviceRepository = serviceRepository;
        this.itemRepository = itemRepository;
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
        Optional<Service> optionalService = serviceRepository.findById(serviceId);
        if (optionalService.isPresent()) {
            Service service = optionalService.get();
            Long items = itemRepository.countByBuyable(service);
            if (items == 0) {
                serviceRepository.delete(service);
            } else {
                service.setDeleted(true);
                serviceRepository.save(service);
            }
            return true;
        }
        return false;
    }
}
