package com.marcinadd.repairshop.form;

import com.marcinadd.repairshop.form.auth.FormAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("forms")
@CrossOrigin("*")
public class FormController {
    private final FormService formService;
    private final FormAuthService formAuthService;

    public FormController(FormService formService, FormAuthService formAuthService) {
        this.formService = formService;
        this.formAuthService = formAuthService;
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

    @GetMapping("{formId}/regenerateClientPasswordToPdf")
    public ResponseEntity<byte[]> regeneratePasswordAndSaveAsPdf(@PathVariable("formId") Long formId) {
        return formAuthService.regeneratePasswordForFormSaveToPdf(formId);
    }

    @PostMapping("{formId}/info")
    public Form checkFormInfo(@PathVariable Long formId, @RequestBody String password) {
        return formAuthService.getFormInfo(formId, password);
    }
}
