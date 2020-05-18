package com.marcinadd.repairshop.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marcinadd.repairshop.form.Form;
import com.marcinadd.repairshop.repairable.Repairable;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Form> forms;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(getId(), client.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
