package com.groupb.checkout;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

class ProceduralCheckoutExample {
    Receipt createReceipt(String regionName, double subtotal) {
        double taxRate;
        NumberFormat currencyFormatter;

        if (regionName.equalsIgnoreCase("United States")) {
            taxRate = 0.0725;
            currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        } else if (regionName.equalsIgnoreCase("United Kingdom")) {
            taxRate = 0.20;
            currencyFormatter = NumberFormat.getCurrencyInstance(Locale.UK);
        } else if (regionName.equalsIgnoreCase("Canada")) {
            taxRate = 0.13;
            currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
            currencyFormatter.setCurrency(Currency.getInstance("CAD"));
        } else {
            throw new IllegalArgumentException("Unsupported region: " + regionName);
        }

        double tax = subtotal * taxRate;
        double total = subtotal + tax;

        return new Receipt(
                currencyFormatter.format(subtotal),
                currencyFormatter.format(tax),
                currencyFormatter.format(total)
        );
    }
}
