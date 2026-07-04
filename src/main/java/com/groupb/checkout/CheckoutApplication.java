package com.groupb.checkout;

public class CheckoutApplication {
    public static void main(String[] args) {
        RegionFactoryRegistry registry = new RegionFactoryRegistry();
        ConsoleCheckoutApp app = new ConsoleCheckoutApp(registry);
        app.run();
    }
}
