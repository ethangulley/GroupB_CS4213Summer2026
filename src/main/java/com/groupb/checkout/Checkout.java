package com.groupb.checkout;

class Checkout {
    private final TaxCalculator taxCalculator;
    private final CurrencyFormatter currencyFormatter;

    Checkout(RegionFactory factory) {
        this.taxCalculator = factory.createTaxCalculator();
        this.currencyFormatter = factory.createCurrencyFormatter();
    }

    Receipt createReceipt(double subtotal) {
        double tax = taxCalculator.calculate(subtotal);
        double total = subtotal + tax;

        return new Receipt(
                currencyFormatter.format(subtotal),
                currencyFormatter.format(tax),
                currencyFormatter.format(total)
        );
    }
}
