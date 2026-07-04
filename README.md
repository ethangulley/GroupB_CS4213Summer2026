# GroupB_CS4213Summer2026

Java terminal app for the project proposal's Abstract Factory Pattern checkout example.

The CLI supports two hardcoded demo users:

- Admin: `admin` / `password`
- Shopper: `shopper` / `password`

Admins can create a new configurable regional factory by entering a region name, tax
rate, and currency code. Shoppers can then select from the built-in factories and any
factories created by an admin during the same app session. Receipts print the exact
factory used for checkout.

Run:

```bash
javac -d out src/main/java/com/groupb/checkout/*.java
java -cp out com.groupb.checkout.CheckoutApplication
```
