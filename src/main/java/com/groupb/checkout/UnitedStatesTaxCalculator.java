package com.groupb.checkout;

class UnitedStatesTaxCalculator implements TaxCalculator {
    public double calculate(double subtotal) {
        return subtotal * 0.0725;
    }
}
