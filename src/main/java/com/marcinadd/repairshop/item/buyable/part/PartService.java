package com.marcinadd.repairshop.item.buyable.part;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartService {
    private final PartRepository partRepository;

    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    public Part createPart(Part part) {
        return partRepository.save(part);
    }

    public List<Part> getParts() {
        return partRepository.findAll();
    }

    /**
     * Positive value adds; negative removes from inStockQuantity
     */
    synchronized public void changeInStockQuantity(Part part, int change) throws NotEnoughPartsInStockException {
        Integer inStockQuantity = part.getInStockQuantity();
        if (inStockQuantity + change >= 0) {
            part.setInStockQuantity(inStockQuantity + change);
            partRepository.save(part);
        } else {
            throw new NotEnoughPartsInStockException(part.getName(), Math.abs(change), inStockQuantity);
        }
    }
}
