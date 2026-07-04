package com.groupb.checkout;

import java.util.Currency;

class ConfigurableRegionFactory implements RegionFactory {
    private final String regionName;
    private final String factoryName;
    private final double taxRate;
    private final Currency currency;

    ConfigurableRegionFactory(String regionName, String factoryName, double taxRate, Currency currency) {
        this.regionName = regionName;
        this.factoryName = factoryName;
        this.taxRate = taxRate;
        this.currency = currency;
    }

    public TaxCalculator createTaxCalculator() {
        return new FixedRateTaxCalculator(taxRate);
    }

    public CurrencyFormatter createCurrencyFormatter() {
        return new CurrencyCodeFormatter(currency);
    }

    public String regionName() {
        return regionName;
    }

    public String factoryName() {
        return factoryName;
    }
}
