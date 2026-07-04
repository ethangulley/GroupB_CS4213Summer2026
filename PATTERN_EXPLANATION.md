# Abstract Factory Pattern Explanation

## Design Problem

The checkout system needs different tax and currency rules for different customer regions. A simple procedural approach would place regional logic directly inside checkout code with `if` or `switch` statements.

That creates a problem because every new region requires editing the checkout logic, which makes the system harder to read, test, and extend.

## Region Implementation in Previous Application Version without the Abstract Factory Pattern.

`ProceduralCheckoutExample` shows the same checkout idea without a design pattern. It keeps
the region rules in one method with an `if / else if / else` chain:

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

This class is useful for comparison, but it shows the weakness from the proposal: checkout
logic, tax policy, currency formatting, and region selection are all mixed together. Adding a
new country means editing this method again, which increases regression risk as the chain grows.
It also cannot naturally support admin-created runtime factories without adding more conditionals
or redesigning the code.

## Pattern Intent

The Abstract Factory Pattern provides an interface for creating related objects without tying the client code to their concrete classes.

In this prototype, each regional factory creates the services needed for that region:

- A tax calculator
- A currency formatter

The app ships with concrete U.S. and U.K. factories. An admin can also create a configurable
factory at runtime by supplying a region name, tax rate, and currency code. The shopper flow
uses the same registry for both built-in factories and admin-created factories.

## Interactions

1. A user logs in as either `admin` or `shopper`.
2. Admins can add a new `ConfigurableRegionFactory` to `RegionFactoryRegistry`.
3. Shoppers select a checkout region from the terminal menu.
4. `RegionFactoryRegistry` returns the matching `RegionFactory`.
5. `ConsoleCheckoutApp` gives the factory to `Checkout`.
6. The factory creates the matching tax calculator and currency formatter.
7. `Checkout` uses those objects without knowing their concrete classes.

## How It Improves the Design

- Maintainability: Regional rules are separated from checkout logic.
- Flexibility: A new region can be added with a new factory and related classes.
- Readability: Checkout code focuses only on creating the receipt.
- Extensibility: Tax and currency behavior can change per region without rewriting checkout.

Compared to `ProceduralCheckoutExample`, this avoids placing all regional tax and formatting
rules inside one large conditional block. `Checkout` only asks a `RegionFactory` for the matching
objects, so the checkout workflow stays stable when regions change.

## Tradeoffs and Limitations

- More classes are required than a simple procedural solution.
- The pattern can feel unnecessary for very small systems.
- Adding a new service type, such as shipping rules, would require updating the factory interface and every factory implementation.
