package com.marcinadd.repairshop.repairable;

import com.marcinadd.repairshop.client.Client;
import com.marcinadd.repairshop.client.ClientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("repairables")
@CrossOrigin("*")
public class RepairableController {

    private final RepairableService repairableService;
    private final ClientRepository clientRepository;

    public RepairableController(RepairableService repairableService, ClientRepository clientRepository) {
        this.repairableService = repairableService;
        this.clientRepository = clientRepository;
    }

    @PostMapping
    public Repairable createRepairable(@RequestBody Repairable repairable) {
        return repairableService.createRepairable(repairable);
    }

    @GetMapping
    public List<Repairable> getRepairablesByOwnerId(@RequestParam("ownerId") Long ownerId) {
        Optional<Client> byId = clientRepository.findById(ownerId);
        return byId.map(repairableService::findByOwner).orElse(null);
    }
}
