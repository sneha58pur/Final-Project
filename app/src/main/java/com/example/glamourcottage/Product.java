package com.example.glamourcottage;

public class Product {
    private String name;
    private double price;
    private byte[] image;

    public Product(String name, double price, byte[] image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public byte[] getImage() {
        return image;
    }
}
