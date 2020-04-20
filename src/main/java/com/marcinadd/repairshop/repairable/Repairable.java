package com.marcinadd.repairshop.repairable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.marcinadd.repairshop.client.Client;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Repairable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String serial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Client owner;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long ownerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repairable that = (Repairable) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
