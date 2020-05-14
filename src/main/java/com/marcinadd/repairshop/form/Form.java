package com.marcinadd.repairshop.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.marcinadd.repairshop.client.Client;
import com.marcinadd.repairshop.item.Item;
import com.marcinadd.repairshop.repairable.Repairable;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Item> items;

    @JsonIgnore
    private String password;

    @CreatedDate
    private Date createdDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Form form = (Form) o;
        return Objects.equals(getId(), form.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
