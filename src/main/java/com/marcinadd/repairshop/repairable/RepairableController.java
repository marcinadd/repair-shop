package com.marcinadd.repairshop.repairable;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("repairables")
@CrossOrigin("*")
public class RepairableController {

    private final RepairableService repairableService;

    public RepairableController(RepairableService repairableService) {
        this.repairableService = repairableService;
    }

    @PostMapping
    public Repairable createRepairable(@RequestBody Repairable repairable) {
        return repairableService.createRepairable(repairable);
    }
}
