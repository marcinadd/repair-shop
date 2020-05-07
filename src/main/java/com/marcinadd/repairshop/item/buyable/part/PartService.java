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
}
