package com.marcinadd.repairshop.item.buyable.part;

import com.marcinadd.repairshop.item.buyable.BuyableService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartService {
    private final PartRepository partRepository;
    private final BuyableService buyableService;

    public PartService(PartRepository partRepository, BuyableService buyableService) {
        this.partRepository = partRepository;
        this.buyableService = buyableService;
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

    public Part updatePart(Long partId, Part updatedValues) {
        Optional<Part> optionalPart = partRepository.findById(partId);
        if (optionalPart.isPresent()) {
            Part part = optionalPart.get();
            if (updatedValues.getInStockQuantity() != null) {
                part.setInStockQuantity(updatedValues.getInStockQuantity());
            }
            if (updatedValues.getPrice() != null) {
                part.setPrice(updatedValues.getPrice());
            }
            return part;
        }
        return null;
    }

    public boolean deletePart(Long partId) {
        return buyableService.deleteBuyable(partId);
    }
}
