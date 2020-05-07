package com.marcinadd.repairshop.item.buyable.part;

import com.marcinadd.repairshop.item.buyable.Buyable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Part extends Buyable {
    private int inStockQuantity;
    private boolean unlimited;
}
