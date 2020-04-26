package com.marcinadd.repairshop.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marcinadd.repairshop.client.Client;
import com.marcinadd.repairshop.item.Item;
import com.marcinadd.repairshop.repairable.Repairable;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Form {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Repairable repairable;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long clientId;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long repairableId;

    private String description;

    private Status status;
    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;
}
