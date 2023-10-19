package org.example;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSystem {
    private Customer customer;
    private final Map<Product, Integer> basket = new HashMap<>();

    public CheckoutSystem(Customer currentCustomer) {
        this.customer = currentCustomer;
    }

    public int getBasketSize() {
        return basket.size();
    }


    public void registerProduct(Product product) {
        if (!basket.containsKey(product)) {
            basket.put(product, 1);
        } else {
            int quantity = basket.get(product);
            quantity++;
            basket.put(product, quantity); // Skriver över tidigare quantity med +1
        }
    }


    public Product getProduct(String productName) {
        Product p = null;
        for (Product product : basket.keySet()) {
            if (productName.equals(product.getName())) {
                p = product;
            }
        }
        return p;
    }

    public void removeProduct(String productName) {
        Product product = getProduct(productName);

        if (!basket.containsKey(product)) {
            throw new IllegalArgumentException("Basket is already empty");
        }

        if (basket.get(product) == 1) { // Om det bara finns 1 av produkten tas entry bort
            basket.remove(product);
        } else {
            int quantity = basket.get(product);
            quantity--;
            basket.put(product, quantity); // Skriver över tidigare quantity med -1
        }
    }

    public boolean contains(Product product) {
        return basket.containsKey(product);
    }

    public double getTotal() {
        // TODO Here will all logic be
        // Produktkategorirabatter
        // Produktrabatter
        // Åldersgräns, kundens ålder vs produkt
        // Rabatt giltighetstid?

        // kolla kundens berättigade rabatt
        // kolla membership och rabatter

        double totalSum = 0;
        for (Map.Entry<Product, Integer> entry : basket.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double quantityDiscount = calculateQuantityDiscount(product);

            totalSum += (product.getPrice() * quantity) - quantityDiscount;
        }

        double membershipDiscount = 0;
        if (customer.getMembership() != null) {
            membershipDiscount = getMembershipDiscount();
        }

        return (totalSum * (1 - membershipDiscount));
    }

    private double calculateQuantityDiscount(Product product) {
        Pair<Integer, Integer> quantityDiscount = product.getQuantityDiscount();
        if (quantityDiscount != null) {
            int toBeBought = basket.get(product);
            int take = quantityDiscount.getLeft();

            if (toBeBought >= take) {
                int totalPayFor = 0;
                int payFor = quantityDiscount.getRight();
                int temp = toBeBought;

                while (temp >= take) {
                    totalPayFor += temp - payFor;
                    temp = -take;
                }

                return (totalPayFor * product.getPrice());
            }
        }
        return 0;
    }

    private double getMembershipDiscount() {
        return switch (customer.getMembership().getLevel()) {
            case "Bronze" -> 0.01;
            case "Silver" -> 0.02;
            case "Gold" -> 0.05;
            default -> 0;
        };
    }

    public void checkout() {
        double total = getTotal();
        try {
            customer.pay(total);
            basket.clear();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }
}
