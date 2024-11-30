package com.example.glamourcottage;
public class Order {
    private final String productName;
    private final double productPrice;
    private final int quantity;
    private final String productSize;

    public Order(String productName, double productPrice, int quantity, String productSize) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.productSize = productSize;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductSize() {
        return productSize;
    }
}
