package com.groupb.checkout;

interface RegionFactory {
    TaxCalculator createTaxCalculator();

    CurrencyFormatter createCurrencyFormatter();

    String regionName();

    String factoryName();
}
