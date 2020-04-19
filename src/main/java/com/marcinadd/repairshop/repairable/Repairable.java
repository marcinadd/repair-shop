package com.marcinadd.repairshop.repairable;

import com.marcinadd.repairshop.client.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class Repairable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String serial;

    @ManyToOne
    private Client owner;
}
