package com.marcinadd.repairshop.pdf.generator;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.marcinadd.repairshop.item.Item;
import com.marcinadd.repairshop.pdf.CurrencyService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class ItemListGenerator {
    private final CurrencyService currencyService;

    public ItemListGenerator(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    private void addTableHeader(PdfPTable table, MessageSource messageSource) {
        Stream.of(
                messageSource.getMessage("pdf.form.name", null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("pdf.form.item.price", null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("pdf.form.quantity", null, LocaleContextHolder.getLocale()),
                messageSource.getMessage("pdf.form.total.price", null, LocaleContextHolder.getLocale())
        ).forEach(element -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(element));
            table.addCell(header);
        });
    }

    private List<TableRow> prepareData(List<Item> items) {
        List<TableRow> tableRows = new ArrayList<>();
        items.forEach(item -> {
            tableRows.add(new TableRow(
                    item.getBuyable().getName(),
                    item.getItemPrice(),
                    item.getQuantity(),
                    item.getItemPrice().multiply(new BigDecimal(item.getQuantity()))
            ));
        });
        return tableRows;
    }

    private void addTableCell(PdfPTable table, Object object) {
        PdfPCell cell = new PdfPCell();
        cell.setPhrase(new Phrase(String.valueOf(object)));
        table.addCell(cell);
    }

    private void addTableContent(PdfPTable table, List<Item> items) {
        List<TableRow> tableRows = prepareData(items);
        tableRows.forEach(tableRow -> {
            addTableCell(table, tableRow.name);
            addTableCell(table, currencyService.formatBigDecimal(tableRow.itemPrice));
            addTableCell(table, tableRow.quantity);
            addTableCell(table, currencyService.formatBigDecimal(tableRow.totalPrice));
        });
    }

    public PdfPTable formItemListGenerator(List<Item> items, MessageSource messageSource) {
        PdfPTable table = new PdfPTable(4);
        addTableHeader(table, messageSource);
        addTableContent(table, items);
        return table;
    }

    public BigDecimal countTotalPrice(List<Item> items) {
        final BigDecimal[] sum = {new BigDecimal(0)};
        items.forEach(item -> {
            BigDecimal itemTotalPrice = item.getItemPrice().multiply(new BigDecimal(item.getQuantity()));
            sum[0] = sum[0].add(itemTotalPrice);
        });
        return sum[0];
    }

    private static class TableRow {
        private String name;
        private BigDecimal itemPrice;
        private Integer quantity;
        private BigDecimal totalPrice;

        public TableRow(String name, BigDecimal itemPrice, Integer quantity, BigDecimal totalPrice) {
            this.name = name;
            this.itemPrice = itemPrice;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
        }
    }
}
