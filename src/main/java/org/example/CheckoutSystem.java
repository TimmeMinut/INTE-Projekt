package org.example;

import java.util.ArrayList;

public class CheckoutSystem {
    // TODO Different data structure, HashMap?
    private ArrayList<Product> basket = new ArrayList<>();

    public void addProduct(Product product) {
        basket.add(product);
    }

    public Product getProduct(String productName) {
        Product p = null;
        for (Product product: basket) {
            if (productName.equals(product.getName())) {
                p = product;
            }
        }
        return p;
    }

    public void removeProduct(String productName) {
        Product product = getProduct(productName);
        if (basket.contains(product)) {
            basket.remove(product);
        }
    }

    public boolean contains(Product product) {
        if (basket.contains(product)) {
            return true;
        }
        return false;
    }
}
