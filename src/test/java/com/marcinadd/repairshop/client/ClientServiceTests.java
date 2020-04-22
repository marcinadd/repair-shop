package com.marcinadd.repairshop.client;

import com.marcinadd.repairshop.RepairShopApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
@AutoConfigureMockMvc
public class ClientServiceTests {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    private Client client1;
    private Client client2;
    private Client client3;

    @Before
    public void init() {
        client1 = clientRepository.save(Client.builder()
                .lastName("Abcdef")
                .build());
        client2 = clientRepository.save(Client.builder()
                .lastName("Abcghd")
                .build());
        client3 = clientRepository.save(Client.builder()
                .lastName("Adf")
                .build());
    }

    @Test
    public void whenFindUsersByLastName_shouldReturnUsers() {
        List<Client> clientsWhereLastNameStartsWith = clientService.findClientsWhereLastNameStartsWith("abc");
        assertThat(clientsWhereLastNameStartsWith, hasItem(client1));
        assertThat(clientsWhereLastNameStartsWith, hasItem(client2));
        assertThat(clientsWhereLastNameStartsWith.size(), is(2));
    }

    @Test
    public void whenUpdateUser_shouldReturnUpdatedUsers() {
        Client toUpdateInfo = Client.builder()
                .id(client1.getId())
                .firstName("NewName")
                .lastName("NewName")
                .phone("+48123456789")
                .email("newmail@example.com")
                .repairables(null)
                .build();
        Client updatedClient = clientService.updateClient(toUpdateInfo);
        assertThat(updatedClient.getFirstName(), is(toUpdateInfo.getFirstName()));
        assertThat(updatedClient.getLastName(), is(toUpdateInfo.getLastName()));
        assertThat(updatedClient.getPhone(), is(toUpdateInfo.getPhone()));
        assertThat(updatedClient.getEmail(), is(toUpdateInfo.getEmail()));
    }


}
