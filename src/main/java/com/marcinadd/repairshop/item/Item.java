package com.marcinadd.repairshop.item;

import com.marcinadd.repairshop.item.buyable.Buyable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Buyable buyable;
    private BigDecimal itemPrice;
    private Integer quantity;
}