package com.marcinadd.repairshop.item.buyable.part;

public class NotEnoughPartsInStockException extends Exception {
    private final String partName;
    private final Integer expected;
    private final Integer inStock;

    public NotEnoughPartsInStockException(String partName, Integer expected, Integer inStock) {
        this.partName = partName;
        this.expected = expected;
        this.inStock = inStock;
    }


    public String toString() {
        return String.format("Not enough parts in stock expected %d %ss but get %d", expected, partName, inStock);
    }
}
