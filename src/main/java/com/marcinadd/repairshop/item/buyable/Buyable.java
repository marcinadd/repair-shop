package com.marcinadd.repairshop.item.buyable;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Buyable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;
    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean deleted;
}
