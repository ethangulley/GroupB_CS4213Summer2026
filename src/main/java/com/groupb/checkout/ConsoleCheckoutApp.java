package com.groupb.checkout;

import java.io.Console;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

class ConsoleCheckoutApp {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password";
    private static final String SHOPPER_USERNAME = "shopper";
    private static final String SHOPPER_PASSWORD = "password";

    private final RegionFactoryRegistry registry;
    private final Scanner scanner;

    ConsoleCheckoutApp(RegionFactoryRegistry registry) {
        this.registry = registry;
        this.scanner = new Scanner(System.in);
    }

    void run() {
        System.out.println("Regional Checkout App");

        while (true) {
            printLoginMenu();
            String choice = readLine("Selection: ");

            if (choice.equals("0")) {
                System.out.println("Goodbye.");
                return;
            }

            if (choice.equals("1")) {
                login();
            } else {
                System.out.println("Invalid selection.");
            }
        }
    }

    private void printLoginMenu() {
        System.out.println();
        System.out.println("1. Login");
        System.out.println("0. Exit");
    }

    private void login() {
        String username = readRequiredLine("Username: ");
        String password = readPassword("Password: ");

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            runAdminMenu();
        } else if (username.equals(SHOPPER_USERNAME) && password.equals(SHOPPER_PASSWORD)) {
            runShopperMenu();
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private void runAdminMenu() {
        System.out.println();
        System.out.println("Admin logged in.");

        while (true) {
            System.out.println();
            System.out.println("Admin menu");
            System.out.println("1. Create new factory");
            System.out.println("2. List factories");
            System.out.println("0. Logout");

            String choice = readLine("Selection: ");

            if (choice.equals("0")) {
                System.out.println("Admin logged out.");
                return;
            }

            if (choice.equals("1")) {
                createFactory();
            } else if (choice.equals("2")) {
                printFactories();
            } else {
                System.out.println("Invalid selection.");
            }
        }
    }

    private void createFactory() {
        System.out.println();
        System.out.println("Create regional factory");

        String regionName = readRequiredLine("Region display name: ");
        String factoryName = readFactoryName(regionName);
        double taxRate = readTaxRate();
        Currency currency = readCurrency();

        registry.addFactory(new ConfigurableRegionFactory(regionName, factoryName, taxRate, currency));

        System.out.println("Created factory: " + factoryName + " for " + regionName + ".");
    }

    private String readFactoryName(String regionName) {
        String suggestedName = defaultFactoryName(regionName);

        while (true) {
            String input = readLine("Factory name [" + suggestedName + "]: ");
            String factoryName = input.isBlank() ? suggestedName : input;

            if (!factoryName.matches("[A-Za-z][A-Za-z0-9_]*")) {
                System.out.println("Use a class-style name with letters, numbers, or underscores.");
                continue;
            }

            if (registry.containsFactoryName(factoryName)) {
                System.out.println("A factory with that name already exists.");
                continue;
            }

            return factoryName;
        }
    }

    private String defaultFactoryName(String regionName) {
        StringBuilder builder = new StringBuilder();
        boolean capitalizeNext = true;

        for (int index = 0; index < regionName.length(); index++) {
            char current = regionName.charAt(index);

            if (Character.isLetterOrDigit(current)) {
                builder.append(capitalizeNext ? Character.toUpperCase(current) : current);
                capitalizeNext = false;
            } else {
                capitalizeNext = true;
            }
        }

        String baseName = builder.length() == 0 ? "CustomRegion" : builder.toString();

        if (!Character.isLetter(baseName.charAt(0))) {
            baseName = "Custom" + baseName;
        }

        if (!baseName.endsWith("Factory")) {
            baseName += "Factory";
        }

        return baseName;
    }

    private double readTaxRate() {
        while (true) {
            String input = readLine("Tax rate percentage (7.25 for 7.25%): ");

            try {
                double percentage = Double.parseDouble(input);

                if (percentage >= 0) {
                    return percentage / 100.0;
                }
            } catch (NumberFormatException ignored) {
            }

            System.out.println("Enter a valid non-negative percentage.");
        }
    }

    private Currency readCurrency() {
        while (true) {
            String currencyCode = readRequiredLine("Currency code (USD, GBP, EUR): ").toUpperCase(Locale.ROOT);

            try {
                return Currency.getInstance(currencyCode);
            } catch (IllegalArgumentException ignored) {
                System.out.println("Enter a valid ISO 4217 currency code.");
            }
        }
    }

    private void printFactories() {
        System.out.println();
        System.out.println("Available factories:");

        List<RegionFactory> factories = registry.factories();
        for (int index = 0; index < factories.size(); index++) {
            RegionFactory factory = factories.get(index);
            System.out.println((index + 1) + ". " + factory.regionName() + " -> " + factory.factoryName());
        }
    }

    private void runShopperMenu() {
        System.out.println();
        System.out.println("Shopper logged in.");

        while (true) {
            printCheckoutMenu();
            String choice = readLine("Selection: ");

            if (choice.equals("0")) {
                System.out.println("Shopper logged out.");
                return;
            }

            RegionFactory factory = registry.findByMenuChoice(choice);

            if (factory == null) {
                System.out.println("Invalid selection.");
                continue;
            }

            double subtotal = readSubtotal();
            Checkout checkout = new Checkout(factory);
            Receipt receipt = checkout.createReceipt(subtotal);

            printReceipt(factory, receipt);
        }
    }

    private void printCheckoutMenu() {
        System.out.println();
        System.out.println("Choose checkout region:");

        List<RegionFactory> factories = registry.factories();
        for (int index = 0; index < factories.size(); index++) {
            System.out.println((index + 1) + ". " + factories.get(index).regionName());
        }

        System.out.println("0. Logout");
    }

    private double readSubtotal() {
        while (true) {
            String input = readLine("Order subtotal: ");

            try {
                double subtotal = Double.parseDouble(input);

                if (subtotal >= 0) {
                    return subtotal;
                }
            } catch (NumberFormatException ignored) {
            }

            System.out.println("Enter a valid number.");
        }
    }

    private void printReceipt(RegionFactory factory, Receipt receipt) {
        System.out.println();
        System.out.println("Region:       " + factory.regionName());
        System.out.println("Factory used: " + factory.factoryName());
        System.out.println("Subtotal: " + receipt.subtotal());
        System.out.println("Tax:      " + receipt.tax());
        System.out.println("Total:    " + receipt.total());
    }

    private String readRequiredLine(String prompt) {
        while (true) {
            String input = readLine(prompt);

            if (!input.isBlank()) {
                return input;
            }

            System.out.println("Enter a value.");
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private String readPassword(String prompt) {
        Console console = System.console();

        if (console != null) {
            char[] password = console.readPassword(prompt);

            if (password == null) {
                return "";
            }

            return new String(password);
        }

        return readLine(prompt);
    }
}
