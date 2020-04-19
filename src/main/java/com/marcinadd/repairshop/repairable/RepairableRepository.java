package com.marcinadd.repairshop.repairable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairableRepository extends JpaRepository<Repairable, Long> {
}
