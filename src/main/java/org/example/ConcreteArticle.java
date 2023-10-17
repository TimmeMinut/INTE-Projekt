package org.example;

public class ConcreteArticle {
    private String name;
    private final double price;
    private ProductCategory productCategory;
    private double discountAmount = 0;

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

    public double getDiscountAmount() { return discountAmount; }

    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }

    public double getPriceAfterDiscounts() { return price - discountAmount; }


}
