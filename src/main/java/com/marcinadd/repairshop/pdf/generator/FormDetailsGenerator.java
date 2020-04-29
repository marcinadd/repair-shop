package com.marcinadd.repairshop.pdf.generator;

import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.marcinadd.repairshop.client.Client;
import com.marcinadd.repairshop.form.Form;
import com.marcinadd.repairshop.repairable.Repairable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FormDetailsGenerator {

    private List<TableRow> prepareData(Form form, MessageSource messageSource) {
        Client client = form.getClient();
        Repairable repairable = form.getRepairable();
        List<TableRow> tableRows = new ArrayList<>();
        tableRows.add(new TableRow(
                messageSource.getMessage("pdf.form.client", null, LocaleContextHolder.getLocale()),
                client.getFirstName() + client.getLastName()
        ));
        tableRows.add(new TableRow(
                messageSource.getMessage("pdf.form.client.phone", null, LocaleContextHolder.getLocale()),
                client.getPhone()
        ));
        tableRows.add(new TableRow(
                messageSource.getMessage("pdf.form.client.email", null, LocaleContextHolder.getLocale()),
                client.getEmail()
        ));
        tableRows.add(new TableRow(
                messageSource.getMessage("pdf.form.repairable.name", null, LocaleContextHolder.getLocale()),
                repairable.getName()
        ));
        tableRows.add(new TableRow(
                messageSource.getMessage("pdf.form.repairable.serial", null, LocaleContextHolder.getLocale()),
                repairable.getSerial()
        ));
        tableRows.add(new TableRow(
                messageSource.getMessage("pdf.form.description", null, LocaleContextHolder.getLocale()),
                form.getDescription()
        ));
        return tableRows;
    }

    public PdfPTable getFormDetails(Form form, MessageSource messageSource) {
        PdfPTable table = new PdfPTable(2);
        List<TableRow> tableRows = prepareData(form, messageSource);
        tableRows.forEach(tableRow -> {
            PdfPCell name = new PdfPCell();
            name.setPhrase(new Phrase(tableRow.name, FontFactory.getFont(FontFactory.HELVETICA)));
            table.addCell(name);

            PdfPCell value = new PdfPCell();
            value.setPhrase(new Phrase(tableRow.name, FontFactory.getFont(FontFactory.HELVETICA)));
            value.setPhrase(new Phrase(tableRow.value));
            table.addCell(value);
        });
        return table;
    }

    private static class TableRow {
        private String name;
        private String value;

        TableRow(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}
