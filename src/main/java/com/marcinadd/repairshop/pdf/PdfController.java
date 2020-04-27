package com.marcinadd.repairshop.pdf;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("pdf")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("forms/{formId}")
    public ResponseEntity<byte[]> makePdf(@PathVariable("formId") Long formId) {
        return pdfService.createPdfFormSummary(formId);
    }
}
