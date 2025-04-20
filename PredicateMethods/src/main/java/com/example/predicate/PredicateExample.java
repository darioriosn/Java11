package com.example.predicate;

import com.example.entities.Product;
import com.example.enums.Category;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateExample {
    public static void main(String[] args) {
        // Sample data: a list of products with their properties
        List<Product> products = Arrays.asList(
                new Product("Laptop", 1200.0, true, Category.ELECTRONICS),
                new Product("Smartphone", 800.0, true, Category.ELECTRONICS),
                new Product("Headphones", 150.0, false, Category.ELECTRONICS),
                new Product("Book", 25.0, true, Category.BOOKS),
                new Product("Notebook", 5.0, true, Category.OFFICE),
                new Product("Pen", 2.0, false, Category.OFFICE),
                new Product("Desk", 300.0, true, Category.FURNITURE),
                new Product("Chair", 150.0, false, Category.FURNITURE),
                new Product("", 0.0, false, Category.UNKNOWN)  // Invalid product
        );

        // Example 1: Basic usage of Predicate.not()
        System.out.println("=== Example 1: Basic Predicate.not() ===");

        List<Product> validProductsOldWay = products.stream()
                .filter(p -> !p.getName().isEmpty())
                .collect(Collectors.toList());

        // New way with Predicate.not
        List<Product> validProductsNewWay = products.stream()
                .filter(Predicate.not(p -> p.getName().isEmpty()))
                .collect(Collectors.toList());

        // Even cleaner with method reference
        List<Product> validProductsMethodRef = products.stream()
                .filter(Predicate.not(Product::hasEmptyName))
                .collect(Collectors.toList());

        System.out.println("Valid products count: " + validProductsMethodRef.size());

        // Example 2: Combining multiple predicates
        System.out.println("\n=== Example 2: Combining predicates ===");

        // Define predicates
        Predicate<Product> isExpensive = p -> p.getPrice() > 1000;
        Predicate<Product> isInStock = Product::isInStock;
        Predicate<Product> isElectronics = p -> p.getCategory().equals(Category.ELECTRONICS);

        //Use peek to look into the first filter
        System.out.println("\n=== Peek into first predicate ===");
        List<Product> electronicsProducts = products.stream()
                .filter(isElectronics)
                .peek(p -> System.out.println("After electronics filter: " + p.getName()))
                .collect(Collectors.toList());

        electronicsProducts.forEach(p ->
                System.out.println("- " + p.getName() + " ($" + p.getPrice() + ")"));

        // Find affordable electronics that are in stock
        List<Product> affordableElectronicsInStock = products.stream()
                .filter(isElectronics)
                .filter(isInStock)
                .filter(Predicate.not(isExpensive))
                .collect(Collectors.toList());

        System.out.println("Affordable electronics in stock:");
        affordableElectronicsInStock.forEach(p ->
                System.out.println("- " + p.getName() + " ($" + p.getPrice() + ")"));

        // Example 3: Using Predicate.not() in complex filtering scenarios
        System.out.println("\n=== Example 3: Complex filtering ===");

        // Group products by availability, excluding products with empty names
        Map<Boolean, List<Product>> productsByAvailability = products.stream()
                .filter(Predicate.not(Product::hasEmptyName))
                .collect(Collectors.groupingBy(Product::isInStock));

        System.out.println("In stock products:");
        productsByAvailability.get(true).forEach(p ->
                System.out.println("- " + p.getName() + " (" + p.getCategory() + ")"));

        System.out.println("\nOut of stock products:");
        productsByAvailability.get(false).forEach(p ->
                System.out.println("- " + p.getName() + " (" + p.getCategory() + ")"));

        // Example 4: Chaining Predicate.not() with other predicate operations
        System.out.println("\n=== Example 4: Chaining predicates ===");

        // Find products that are neither expensive nor out of stock
        Predicate<Product> notExpensiveAndInStock = Predicate.not(isExpensive).and(isInStock);

        List<Product> affordableInStockProducts = products.stream()
                .filter(Predicate.not(Product::hasEmptyName))
                .filter(notExpensiveAndInStock)
                .collect(Collectors.toList());

        System.out.println("Affordable products in stock:");
        affordableInStockProducts.forEach(p ->
                System.out.println("- " + p.getName() + " ($" + p.getPrice() + ")"));

        // Example 5: Using Predicate.not() with custom business logic
        System.out.println("\n=== Example 5: Custom business logic ===");

        // Define a complex business rule
        Predicate<Product> isPromotionEligible = product ->
                product.isInStock() &&
                        product.getPrice() >= 10 &&
                        product.getPrice() <= 500 &&
                        product.getCategory() != Category.UNKNOWN;

        // Find products not eligible for promotion
        List<Product> nonPromotionProducts = products.stream()
                .filter(Predicate.not(Product::hasEmptyName))
                .filter(Predicate.not(isPromotionEligible))
                .collect(Collectors.toList());

        System.out.println("Products not eligible for promotion:");
        nonPromotionProducts.forEach(p ->
                System.out.println("- " + p.getName() + " ($" + p.getPrice() +
                        ", in stock: " + p.isInStock() + ")"));
    }
}




