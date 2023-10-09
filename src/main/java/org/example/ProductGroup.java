package org.example;

import java.util.ArrayList;
import java.util.List;

public class ProductGroup implements Product {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
        // TODO How validate if product belongs to ProductGroup?
    }

    @Override
    public double getPrice() {
        double totalPrice = 0;
        for (Product product: products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }
}
