package com.marcinadd.repairshop.item.buyable;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class ItemForm {
    private Long formId;
    private Long buyableId;
    private Integer quantity;
}
