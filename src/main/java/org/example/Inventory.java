package org.example;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

class Inventory {
    private Map<Product, Integer> products;

    Inventory() {
        products = new HashMap<>();
    }

    void addProduct(Product product, int amount) {
        products.put(product, amount);
    }

    Pair<Product, Integer> getProduct(Product product) {
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            if (product.equals(entry.getKey())) {
                return Pair.of(entry.getKey(), entry.getValue());
            }
        }
        return null;
    }

    void removeProduct(Product product, int quantity) {
        if (products.containsKey(product)) {
            int currentQuantity = products.get(product);

            if (currentQuantity >= quantity) {
                products.put(product, currentQuantity - quantity);
            } else {
                System.out.println("Product quantity is insufficient.");
            }
        } else {
            System.out.println("Product not found in inventory.");
        }
    }

    Map<Product, Integer> getInventory() {
        return products;
    }
}
