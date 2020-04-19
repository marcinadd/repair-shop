package com.marcinadd.repairshop.client;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    Client createClient(Client client) {
        return clientRepository.save(client);
    }

    List<Client> getClients() {
        return clientRepository.findAll();
    }

}
