package com.marcinadd.repairshop.item.buyable.service;

import com.marcinadd.repairshop.item.buyable.Buyable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Service extends Buyable {
    private Boolean deleted;
}
