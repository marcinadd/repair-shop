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
}
