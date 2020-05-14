package com.marcinadd.repairshop.form;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    Page<Form> findAllByStatusOrderByCreatedDateDesc(Status status, Pageable pageable);
}
