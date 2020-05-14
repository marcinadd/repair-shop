package com.marcinadd.repairshop.item.buyable.part;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {
    List<Part> findByDeletedIsFalse();
}
