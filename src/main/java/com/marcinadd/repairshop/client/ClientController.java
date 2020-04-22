package com.marcinadd.repairshop.client;

import org.springframework.web.bind.annotation.*;

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
    public Client findClientById(@PathVariable("id") Long id) {
        return clientService.findById(id);
    }

    @PatchMapping("{id}")
    public Client updateClient(@RequestBody Client client) {
        return clientService.updateClient(client);
    }
}
