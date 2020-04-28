package com.marcinadd.repairshop.form;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("forms")
@CrossOrigin("*")
public class FormController {
    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PostMapping
    public Form createForm(@RequestBody Form form) {
        return formService.createForm(form);
    }

    @GetMapping
    public List<Form> getForms() {
        return formService.findAllForms();
    }

    @GetMapping("{id}")
    public Form getFormById(@PathVariable("id") Long id) {
        return formService.findFormById(id);
    }

    @PatchMapping("{id}")
    public Form patchForm(@PathVariable("id") Long id, @RequestBody Form form) {
        return formService.patchFormById(id, form);
    }
}
