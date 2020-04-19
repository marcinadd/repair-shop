package com.marcinadd.repairshop.client;

import com.marcinadd.repairshop.repairable.Repairable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Repairable> repairables;
}
