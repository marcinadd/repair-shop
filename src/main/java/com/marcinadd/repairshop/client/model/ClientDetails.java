package com.marcinadd.repairshop.client.model;

import com.marcinadd.repairshop.client.Client;
import com.marcinadd.repairshop.form.Form;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ClientDetails implements Serializable {
    private Client client;
    private List<Form> forms;
}
