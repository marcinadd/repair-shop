package com.marcinadd.repairshop.item.buyable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyableRepository extends JpaRepository<Buyable, Long> {
    List<Buyable> findByDeletedIsFalse();
}
