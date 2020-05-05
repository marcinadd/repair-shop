package com.marcinadd.repairshop.pdf;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

@Service
public class CurrencyService {
    private final NumberFormat numberFormat;

    public CurrencyService(Environment env) {
        numberFormat = NumberFormat.getCurrencyInstance(new Locale(Objects.requireNonNull(env.getProperty("app.language")), Objects.requireNonNull(env.getProperty("app.country"))));
    }

    public String formatBigDecimal(BigDecimal value) {
        return numberFormat.format(value);
    }
}
