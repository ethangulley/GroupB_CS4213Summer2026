package com.groupb.checkout;

class Receipt {
    private final String subtotal;
    private final String tax;
    private final String total;

    Receipt(String subtotal, String tax, String total) {
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = total;
    }

    String subtotal() {
        return subtotal;
    }

    String tax() {
        return tax;
    }

    String total() {
        return total;
    }
}
