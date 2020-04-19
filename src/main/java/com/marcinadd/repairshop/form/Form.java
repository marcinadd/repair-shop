package com.marcinadd.repairshop.form;

import com.marcinadd.repairshop.client.Client;
import com.marcinadd.repairshop.repairable.Repairable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class Form {
    @Id
    @GeneratedValue
    private Long id;

    private Client client;

    @ManyToOne
    private Repairable repairable;

    private String description;

    private Status status;


}
