# Abstract Factory Pattern

## Design Problem

The checkout system needs different tax and currency rules for different customer
regions. A procedural solution puts those rules directly inside checkout code with
`if / else` or `switch` statements. As more regions are added, that checkout method
becomes harder to read, test, and maintain.

## No-Pattern Comparison

`ProceduralCheckoutExample` demonstrates the older procedural approach. It keeps
region selection, tax policy, and currency formatting in one method:

```java
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
```

This works for a small demo, but each new region requires editing the same checkout
logic. That increases regression risk and makes runtime admin-created regions hard
to support without adding more conditionals.

## Factory-Based Design

The current app uses the Abstract Factory Pattern. `RegionFactory` defines a common
interface for creating a matching family of checkout objects:

- `TaxCalculator`
- `CurrencyFormatter`

Concrete factories such as `UnitedStatesFactory`, `UnitedKingdomFactory`, and
`ConfigurableRegionFactory` provide the correct objects for each region. `Checkout`
only depends on the `RegionFactory` interface, so it does not need to know which
region is selected or which concrete classes are being used.

## Interaction Flow

1. A user logs in as `admin` or `shopper`.
2. Admins can add a new `ConfigurableRegionFactory`.
3. Shoppers choose a checkout region.
4. `RegionFactoryRegistry` returns the selected `RegionFactory`.
5. `Checkout` asks the factory for a tax calculator and currency formatter.
6. The receipt prints the region, factory used, subtotal, tax, and total.

## Benefits

- Maintainability: regional rules are separated from checkout workflow.
- Extensibility: new regions can be added without rewriting `Checkout`.
- Readability: each factory groups related regional behavior together.
- Flexibility: admin-created factories can be used immediately by shoppers.

## Tradeoffs

- More classes are required than in the procedural version.
- The pattern may be unnecessary for very small systems.
- Adding a new product type, such as shipping rules, would require updating the
  factory interface and each factory implementation.
