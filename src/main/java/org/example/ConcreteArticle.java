package org.example;

import java.util.Objects;

public class ConcreteArticle {
    private String name;
    private final double price;
    private ProductCategory productCategory;
    private double discountAmount = 0;

    // TODO add name
    public ConcreteArticle(double price, ProductCategory productCategory) {
        this.price = price;
        this.productCategory = productCategory;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }

    public double getPriceAfterDiscounts() { return price - discountAmount; }

    @Override
    public int hashCode() {
        // TODO add name
        return Objects.hash(price, productCategory);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof ConcreteArticle) {
            ConcreteArticle object = (ConcreteArticle) obj;
            // TODO: add name
            return this.price == object.getPrice() && this.productCategory == object.getProductCategory();
        }

        return false;
    }

}
