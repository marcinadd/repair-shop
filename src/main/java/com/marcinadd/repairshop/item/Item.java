package com.marcinadd.repairshop.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marcinadd.repairshop.form.Form;
import com.marcinadd.repairshop.item.buyable.Buyable;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Buyable buyable;
    private BigDecimal itemPrice;

    @Min(1)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_sender_id")
    @JsonIgnore
    private Form form;
}
