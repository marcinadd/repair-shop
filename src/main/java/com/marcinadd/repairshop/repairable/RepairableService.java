package com.marcinadd.repairshop.repairable;

import com.marcinadd.repairshop.client.Client;
import com.marcinadd.repairshop.client.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepairableService {

    private final ClientRepository clientRepository;
    private final RepairableRepository repairableRepository;


    public RepairableService(ClientRepository clientRepository, RepairableRepository repairableRepository) {
        this.clientRepository = clientRepository;
        this.repairableRepository = repairableRepository;
    }

    Repairable createRepairable(Repairable repairable) {
        Optional<Client> client = clientRepository.findById(repairable.getOwnerId());
        if (client.isPresent()) {
            repairable.setOwner(client.get());
            return repairableRepository.save(repairable);
        }
        return null;
    }

    List<Repairable> findByOwner(Client owner) {
        return repairableRepository.findByOwner(owner);
    }

}
