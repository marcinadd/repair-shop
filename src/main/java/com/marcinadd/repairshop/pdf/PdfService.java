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
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PdfService {
    private final MessageSource messageSource;
    private final FormRepository formRepository;
    private final FormDetailsGenerator formDetailsGenerator;
    private final ItemListGenerator itemListGenerator;
    private final CurrencyService currencyService;

    public PdfService(MessageSource messageSource, FormRepository formRepository, FormDetailsGenerator formDetailsGenerator, ItemListGenerator itemListGenerator, CurrencyService currencyService) {
        this.messageSource = messageSource;
        this.formRepository = formRepository;
        this.formDetailsGenerator = formDetailsGenerator;
        this.itemListGenerator = itemListGenerator;
        this.currencyService = currencyService;
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

    public ResponseEntity<byte[]> createPdfFormPassword(Form form, String password) {
        HttpHeaders headers = createHttpHeadersForPdf(form.getId() + "-password.pdf");
        try {
            byte[] file = generateClientFormPasswordPdfDocument(form, password);
            return new ResponseEntity<>(file, headers, HttpStatus.OK);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
        Font fontH1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
        Font fontH2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        Font fontH3 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);

        document.add(new Paragraph(messageSource.getMessage("pdf.form.header", new Long[]{form.getId()}, LocaleContextHolder.getLocale()), fontH1));

        document.add(new Paragraph(messageSource.getMessage("pdf.form.details", null, LocaleContextHolder.getLocale()), fontH2));
        document.add(Chunk.NEWLINE);
        document.add(formDetailsGenerator.getFormDetails(form, messageSource));

        if (form.getItems() != null) {
            document.add(new Paragraph(messageSource.getMessage("pdf.form.items", null, LocaleContextHolder.getLocale()), fontH2));
            document.add(Chunk.NEWLINE);
            document.add(itemListGenerator.formItemListGenerator(form.getItems(), messageSource));
            BigDecimal totalPrice = itemListGenerator.countTotalPrice(form.getItems());
            document.add(new Chunk(messageSource.getMessage("pdf.form.sum", null, LocaleContextHolder.getLocale()) + " ", fontH2));
            document.add(new Chunk(currencyService.formatBigDecimal(totalPrice), fontH3));
        }
        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    private byte[] generateClientFormPasswordPdfDocument(Form form, String password) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        Font fontH1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 26, BaseColor.BLACK);
        Font fontH2 = FontFactory.getFont(FontFactory.HELVETICA, 24, BaseColor.BLACK);
        Font fontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 20, BaseColor.BLACK);

        document.add(new Paragraph(messageSource.getMessage("pdf.form.password.h1", null, LocaleContextHolder.getLocale()), fontH1));
        document.add(new Paragraph(messageSource.getMessage("pdf.form.password.message", null, LocaleContextHolder.getLocale()), fontH2));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Chunk(messageSource.getMessage("pdf.form.password.id", null, LocaleContextHolder.getLocale()) + " ", fontBold));
        document.add(new Chunk(String.valueOf(form.getId()), font));
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(new Chunk(messageSource.getMessage("pdf.form.password.password", null, LocaleContextHolder.getLocale()) + " ", fontBold));
        document.add(new Chunk(String.valueOf(password), font));
        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}
