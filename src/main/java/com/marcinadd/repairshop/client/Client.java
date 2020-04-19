package com.marcinadd.repairshop.client;

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
public class Client {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Repairable> repairables;

}
