package com.groupb.checkout;

class UnitedKingdomTaxCalculator implements TaxCalculator {
    public double calculate(double subtotal) {
        return subtotal * 0.20;
    }
}
