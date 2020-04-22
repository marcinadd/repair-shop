package com.marcinadd.repairshop.client;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    List<Client> findClientsWhereLastNameStartsWith(String lastNameStartsWith) {
        return clientRepository.findByLastNameStartingWithIgnoreCase(lastNameStartsWith);
    }

    Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client updateClient(Client client) {
        Optional<Client> toUpdateOptionalClient = clientRepository.findById(client.getId());
        if (toUpdateOptionalClient.isPresent()) {
            Client toUpdateClient = toUpdateOptionalClient.get();
            toUpdateClient.setFirstName(client.getFirstName());
            toUpdateClient.setLastName(client.getLastName());
            toUpdateClient.setEmail(client.getEmail());
            toUpdateClient.setPhone(client.getPhone());
            return clientRepository.save(toUpdateClient);
        }
        return null;
    }
}
