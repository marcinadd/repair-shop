package com.marcinadd.repairshop.item.buyable.part;

import com.marcinadd.repairshop.item.buyable.Buyable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Version;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Part extends Buyable {
    @Version
    private Integer inStockQuantity;
    private Boolean unlimited;
}
