package org.example;

public class ConcreteArticle implements Article {
    private String name;
    private final double price;

    public ConcreteArticle(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscountAmount() { return 0; }
}
