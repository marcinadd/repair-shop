package com.marcinadd.repairshop.item.buyable;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;

@Service
@Getter
@Setter
public class ItemForm {
    private Long formId;
    private Long buyableId;
    @Min(1)
    private Integer quantity;
}
