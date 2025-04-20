package com.example.entities;

import com.example.enums.Category;

public class Product {
    private String name;
    private double price;
    private boolean inStock;
    private Category category;

    public Product(String name, double price, boolean inStock, Category category) {
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public Category getCategory() {
        return category;
    }

    // Utility method for checking if name is empty
    public boolean hasEmptyName() {
        return name == null || name.isEmpty();
    }
}
