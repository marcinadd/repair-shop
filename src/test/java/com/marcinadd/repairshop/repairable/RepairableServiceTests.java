package com.marcinadd.repairshop.repairable;

import com.marcinadd.repairshop.RepairShopApplication;
import com.marcinadd.repairshop.client.Client;
import com.marcinadd.repairshop.client.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
public class RepairableServiceTests {
    @Autowired
    private RepairableService repairableService;

    @Autowired
    private ClientRepository clientRepository;

    private Client owner;

    @Before
    public void init() {
        owner = clientRepository.save(Client.builder()
                .firstName("Sample")
                .build()
        );
    }

    @Test
    public void whenCreateRepairable_shouldReturnRepairable() {
        Repairable repairable = Repairable.builder()
                .name("Sample thing")
                .ownerId(1L)
                .build();
        Repairable saved = repairableService.createRepairable(repairable);
        assertThat(saved.getOwner().getId(), is(owner.getId()));
    }
}
