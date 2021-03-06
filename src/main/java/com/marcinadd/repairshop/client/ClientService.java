package com.marcinadd.repairshop.client;

import com.marcinadd.repairshop.client.model.ClientDetails;
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

    public Client updateClient(Long clientId, Client client) {
        Optional<Client> toUpdateOptionalClient = clientRepository.findById(clientId);
        if (toUpdateOptionalClient.isPresent()) {
            Client toUpdateClient = toUpdateOptionalClient.get();
            if (!client.getFirstName().isBlank()) toUpdateClient.setFirstName(client.getFirstName());
            if (!client.getLastName().isBlank()) toUpdateClient.setLastName(client.getLastName());
            if (!client.getEmail().isBlank()) toUpdateClient.setEmail(client.getEmail());
            if (!client.getPhone().isBlank()) toUpdateClient.setPhone(client.getPhone());
            return clientRepository.save(toUpdateClient);
        }
        return null;
    }

    public boolean deleteClient(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            clientRepository.delete(optionalClient.get());
            return true;
        }
        return false;
    }

    public ClientDetails getClientDetails(Long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            ClientDetails clientDetails = new ClientDetails();
            clientDetails.setClient(client);
            clientDetails.setForms(client.getForms());
            return clientDetails;
        }
        return null;
    }
}
