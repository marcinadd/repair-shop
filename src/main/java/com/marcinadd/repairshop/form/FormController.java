package com.marcinadd.repairshop.form;

import com.marcinadd.repairshop.form.auth.FormAuthService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping(value = "", params = "status")
    public Page<Form> getFormByType(
            @RequestParam Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int itemsPerPage
    ) {
        return formService.findFormByStatusSortByDateGetPage(status, page, itemsPerPage);
    }

    @GetMapping
    public List<Form> getForms() {
        return formService.findAllForms();
    }

    @GetMapping("{id}")
    public Form getFormById(@PathVariable Long id) {
        return formService.findFormById(id);
    }

    @PatchMapping("{id}")
    public Form patchForm(@PathVariable Long id, @RequestBody Form form) {
        return formService.patchFormById(id, form);
    }

    @DeleteMapping("{id}")
    public boolean deleteForm(@PathVariable Long id) {
        return formService.deleteForm(id);
    }

    @GetMapping("{formId}/regenerateClientPasswordToPdf")
    public ResponseEntity<byte[]> regeneratePasswordAndSaveAsPdf(@PathVariable("formId") Long formId) {
        return formAuthService.regeneratePasswordForFormSaveToPdf(formId);
    }

    @PostMapping("{formId}/info")
    public Form checkFormInfo(@PathVariable Long formId, @RequestBody String password) {
        Form form = formAuthService.getFormInfo(formId, password);
        if (form != null) {
            return form;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
