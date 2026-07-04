package com.groupb.checkout;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

class CurrencyCodeFormatter implements CurrencyFormatter {
    private final NumberFormat formatter;

    CurrencyCodeFormatter(Currency currency) {
        this.formatter = NumberFormat.getCurrencyInstance(Locale.US);
        this.formatter.setCurrency(currency);
    }

    public String format(double amount) {
        return formatter.format(amount);
    }
}
