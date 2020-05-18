package com.marcinadd.repairshop.client;

import com.marcinadd.repairshop.client.model.ClientDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("clients")
@CrossOrigin("*")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public Client addClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @GetMapping
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @GetMapping(params = "lastNameStartsWith")
    public List<Client> findClientsWithLastNameStartsWith(@RequestParam String lastNameStartsWith) {
        return clientService.findClientsWhereLastNameStartsWith(lastNameStartsWith);
    }

    @GetMapping("{id}")
    public Client findClientById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @PatchMapping("{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        return clientService.updateClient(id, client);
    }

    @DeleteMapping("{id}")
    public boolean deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id);
    }

    @GetMapping("{id}/details")
    public ClientDetails getClientDetails(@PathVariable Long id) {
        ClientDetails clientDetails = clientService.getClientDetails(id);
        if (clientDetails != null) {
            return clientDetails;
        }
        ;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
