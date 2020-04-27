package com.marcinadd.repairshop.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.marcinadd.repairshop.form.Form;
import com.marcinadd.repairshop.form.FormRepository;
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

    public PdfService(MessageSource messageSource, FormRepository formRepository) {
        this.messageSource = messageSource;
        this.formRepository = formRepository;
    }

    public ResponseEntity<byte[]> createPdfFormSummary(Long formId) {
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

        Font font = FontFactory.getFont(FontFactory.TIMES_BOLD, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk(messageSource.getMessage("pdf.form.header", new Long[]{form.getId()}, LocaleContextHolder.getLocale()), font);
        document.add(chunk);
        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}
