package com.groupb.checkout;

import java.util.Locale;

class UnitedStatesFactory implements RegionFactory {
    public TaxCalculator createTaxCalculator() {
        return new UnitedStatesTaxCalculator();
    }

    public CurrencyFormatter createCurrencyFormatter() {
        return new LocaleCurrencyFormatter(Locale.US);
    }

    public String regionName() {
        return "United States";
    }

    public String factoryName() {
        return "UnitedStatesFactory";
    }
}
