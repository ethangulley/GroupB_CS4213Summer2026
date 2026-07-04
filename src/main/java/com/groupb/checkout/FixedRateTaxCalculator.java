package com.groupb.checkout;

class FixedRateTaxCalculator implements TaxCalculator {
    private final double taxRate;

    FixedRateTaxCalculator(double taxRate) {
        this.taxRate = taxRate;
    }

    public double calculate(double subtotal) {
        return subtotal * taxRate;
    }
}
