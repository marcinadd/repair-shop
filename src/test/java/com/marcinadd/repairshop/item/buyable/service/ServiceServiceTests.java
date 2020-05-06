package com.marcinadd.repairshop.item.buyable.service;

import com.marcinadd.repairshop.RepairShopApplication;
import com.marcinadd.repairshop.item.Item;
import com.marcinadd.repairshop.item.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
public class ServiceServiceTests {
    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void whenUpdateService_shouldUpdateOnlyPrice() {
        Service service = new Service();
        service.setName("Abcd");
        service.setPrice(new BigDecimal("3.25"));
        service = serviceRepository.save(service);

        Service toUpdateService = new Service();
        toUpdateService.setId(service.getId() + 1);
        toUpdateService.setName("Dcba");
        toUpdateService.setPrice(new BigDecimal("4.25"));
        Service updatedService = serviceService.updateService(service.getId(), toUpdateService);

        assertThat(updatedService.getPrice(), equalTo(toUpdateService.getPrice()));
        assertThat(updatedService.getName(), equalTo(service.getName()));
        assertThat(updatedService.getId(), equalTo(service.getId()));
    }

    @Test
    public void whenDeleteUnreferencedService_shouldDeleteIt() {
        Service service = new Service();
        service = serviceRepository.save(service);
        Long id = service.getId();
        assertTrue(serviceService.deleteService(service.getId()));
        assertFalse(serviceRepository.findById(id).isPresent());
    }

    @Test
    public void whenDeleteReferencedService_shouldSetDeletedToTrue() {
        Service service = new Service();
        service = serviceRepository.save(service);
        Long id = service.getId();

        Item item = new Item();
        item.setBuyable(service);
        itemRepository.save(item);

        assertTrue(serviceService.deleteService(service.getId()));
        Optional<Service> optionalService = serviceRepository.findById(id);
        assertTrue(optionalService.isPresent());
        assertTrue(optionalService.get().getDeleted());
    }

    @Test
    public void whenTryToDeleteNotExistingService_shouldReturnFalse() {
        assertFalse(serviceService.deleteService(Long.MAX_VALUE));
    }
}
