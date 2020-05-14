package com.marcinadd.repairshop.item.buyable.part;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("parts")
@CrossOrigin("*")
public class PartController {
    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }

    @PostMapping
    public Part createPart(@RequestBody Part part) {
        return partService.createPart(part);
    }

    @GetMapping
    public List<Part> getParts() {
        return partService.getParts();
    }

    @PatchMapping("{id}")
    public Part updatePart(@PathVariable Long id, @RequestBody Part part) {
        return partService.updatePart(id, part);
    }

    @DeleteMapping("{id}")
    public boolean deletePart(@PathVariable Long id) {
        return partService.deletePart(id);
    }
}
