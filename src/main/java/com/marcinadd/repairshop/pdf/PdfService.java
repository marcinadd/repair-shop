package com.marcinadd.repairshop.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.marcinadd.repairshop.form.Form;
import com.marcinadd.repairshop.form.FormRepository;
import com.marcinadd.repairshop.pdf.generator.FormDetailsGenerator;
import com.marcinadd.repairshop.pdf.generator.ItemListGenerator;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

@Service
public class PdfService {
    private final MessageSource messageSource;
    private final FormRepository formRepository;
    private final FormDetailsGenerator formDetailsGenerator;
    private final ItemListGenerator itemListGenerator;

    public PdfService(MessageSource messageSource, FormRepository formRepository, FormDetailsGenerator formDetailsGenerator, ItemListGenerator itemListGenerator) {
        this.messageSource = messageSource;
        this.formRepository = formRepository;
        this.formDetailsGenerator = formDetailsGenerator;
        this.itemListGenerator = itemListGenerator;
    }

    ResponseEntity<byte[]> createPdfFormSummary(Long formId) {
        Optional<Form> optionalForm = formRepository.findById(formId);
        if (optionalForm.isPresent()) {
            Form form = optionalForm.get();
            HttpHeaders headers = createHttpHeadersForPdf(form.getId() + ".pdf");
            try {
                byte[] file = generateFormSummaryPdfDocument(form);
                return new ResponseEntity<>(file, headers, HttpStatus.OK);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private HttpHeaders createHttpHeadersForPdf(String filename) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.setContentDispositionFormData(filename, filename);
        httpHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return httpHeaders;
    }

    private byte[] generateFormSummaryPdfDocument(Form form) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
        Chunk chunk = new Chunk(messageSource.getMessage("pdf.form.header", new Long[]{form.getId()}, LocaleContextHolder.getLocale()), font);
        document.add(chunk);

        document.add(formDetailsGenerator.getFormDetails(form, messageSource));
        if (form.getItems() != null)
            document.add(itemListGenerator.formItemListGenerator(form.getItems(), messageSource));
        document.close();
        return byteArrayOutputStream.toByteArray();
    }

}
