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
}
