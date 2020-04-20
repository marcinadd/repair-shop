package com.marcinadd.repairshop.repairable;

import com.marcinadd.repairshop.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairableRepository extends JpaRepository<Repairable, Long> {
    List<Repairable> findByOwner(Client client);
}
