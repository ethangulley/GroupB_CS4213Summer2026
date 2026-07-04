package com.groupb.checkout;

import java.text.NumberFormat;
import java.util.Locale;

class LocaleCurrencyFormatter implements CurrencyFormatter {
    private final NumberFormat formatter;

    LocaleCurrencyFormatter(Locale locale) {
        this.formatter = NumberFormat.getCurrencyInstance(locale);
    }

    public String format(double amount) {
        return formatter.format(amount);
    }
}
