package com.groupb.checkout;

import java.util.Locale;

class UnitedKingdomFactory implements RegionFactory {
    public TaxCalculator createTaxCalculator() {
        return new UnitedKingdomTaxCalculator();
    }

    public CurrencyFormatter createCurrencyFormatter() {
        return new LocaleCurrencyFormatter(Locale.UK);
    }

    public String regionName() {
        return "United Kingdom";
    }

    public String factoryName() {
        return "UnitedKingdomFactory";
    }
}
